package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.dto.BankAccountResponse;
import dot.ai.dotnibssmoc.dto.NameEnquiryRequest;
import dot.ai.dotnibssmoc.dto.TransferRequest;
import dot.ai.dotnibssmoc.dto.TransferResponse;
import dot.ai.dotnibssmoc.exceptions.InsufficientFundException;
import dot.ai.dotnibssmoc.model.Bank;
import dot.ai.dotnibssmoc.model.FinancialTransaction;
import dot.ai.dotnibssmoc.model.TransactionHistory;
import dot.ai.dotnibssmoc.model.Wallet;
import dot.ai.dotnibssmoc.model.enums.Status;
import dot.ai.dotnibssmoc.repository.BankRepository;
import dot.ai.dotnibssmoc.repository.TransactionRepository;
import dot.ai.dotnibssmoc.repository.WalletRepository;
import dot.ai.dotnibssmoc.security.PassportProvider;
import dot.ai.dotnibssmoc.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final WalletRepository walletRepository;

    private final BankRepository bankRepository;
    private final RestTemplate restTemplate;

    private final TransactionRepository transactionRepository;

    private final PassportProvider passportProvider;

    private final TransactionHistoryService transactionHistoryService;

    private final PlatformService platformService;

    @Value("${transaction.fee.percentage}")
    private Float transactionFeePercentage;
    @Value("${transaction.fee.cap}")
    private Float transactionFeeCapAt;

    private final String TRANSACTION_REFERENCE="transRef";
    private final String TRANSACTION_ID="transactionId";
    private final String BENEFACTOR_ACCOUNT_NUMBER="benefactorAccountNumber";
    private final String BENEFACTOR_ACCOUNT_NAME="benefactorAccountName";
    private final String AMOUNT="amount";
    private  final String REMARK="remark";

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);



    @Override
    public BankAccountResponse nameEnquiry(NameEnquiryRequest request) {
        return platformService.nameEnquiry(request);
    }

    @Override
    public String transactionStatusEnquiry(String transactionReference) {

        var transaction=transactionRepository.findByTransRef(transactionReference).orElseThrow(
                ()->new EntityNotFoundException("No such transaction exists")
        );

        return transaction.getStatus();
    }

    @Override
    @Transactional
    public TransferResponse acceptTransfer(TransferRequest request) {

        Bank sourceBank=passportProvider.getSourceBank();

        if(sourceBank==null) throw new EntityNotFoundException("Cannot find source bank details");

        var destinationBank= bankRepository.findByBankCode(request.getDestinationBankCode()).orElseThrow(
                ()->new EntityNotFoundException("INVALID BANK-CODE")
        );


        FinancialTransaction transaction=new FinancialTransaction();

        transaction.setStatus(Status.PENDING.getCode());
        transaction.setAmount(request.getAmount());
        transaction.setRemark(request.getRemark());
        transaction.setSourceBankCode(sourceBank.getBankCode());
        transaction.setTransRef(UUID.randomUUID().toString());
        transaction.setBenefactorAccountNumber(request.getBenefactorAccountNumber());
        transaction.setBenefactorAccountName(request.getBenefactorAccountName());
        transaction.setDestinationBankCode(destinationBank.getBankCode());
        transaction.setCreditorAccountName(request.getCreditorAccountName());
        transaction.setCreditorAccountNumber(request.getCreditorAccountNumber());
        transaction.setIsCommissionWorthy(false);

        var fee = computeTransactionFee(transaction.getAmount());
        transaction.setFee(fee);

        var totalAmountDeductible=transaction.getAmount().add(fee);

        logger.info("transaction record initiated for source bank with bank-code: "+transaction.getSourceBankCode());

        var transferCompletion = CompletableFuture.runAsync(() -> {
            processTransferRequest(transaction, sourceBank, totalAmountDeductible);
        });

        transferCompletion.thenRun(() -> {
            CompletableFuture.supplyAsync(() -> processCreditImpact(transaction, destinationBank));
            CompletableFuture.runAsync(() -> transactionHistoryService.create(TransactionHistory.of(transaction)));
        });

        return new TransferResponse(transaction.getTransRef(), Status.SENT.getDescription());

    }


    @Synchronized
    private void processTransferRequest(FinancialTransaction transaction, Bank sourceBank,BigDecimal totalAmountDeductible) {

        Wallet sourceWallet = walletRepository.findByBankCode(sourceBank.getBankCode())
                .orElseThrow(() -> new EntityNotFoundException("Source bank wallet not found"));

        Wallet destinationWallet = walletRepository.findByBankCode(transaction.getDestinationBankCode())
                .orElseThrow(() -> new EntityNotFoundException("Destination bank wallet not found"));


        if (sourceWallet.getBalance().compareTo(totalAmountDeductible) < 0) {
            throw new InsufficientFundException("Insufficient fund");
        }

        sourceWallet.setBalance(sourceWallet.getBalance().subtract(transaction.getAmount()));
        destinationWallet.setBalance(destinationWallet.getBalance().add(transaction.getAmount()));

        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);
        transaction.setStatus(Status.SENT.getCode());

        transactionRepository.save(transaction);
        logger.info("transaction record saved for transaction-reference: "+transaction.getTransRef());
        logger.info("money pushed to destination bank: "+transaction.getDestinationBankCode());

    }

    private BigDecimal computeTransactionFee(BigDecimal amount) {

        BigDecimal feeValue;
        feeValue= amount.multiply(BigDecimal.valueOf(transactionFeePercentage/100));
        if(feeValue.compareTo(BigDecimal.valueOf(transactionFeeCapAt))<0){
            return feeValue;
        }
        return BigDecimal.valueOf(transactionFeeCapAt);
    }

    private CompletableFuture<Void> processCreditImpact(FinancialTransaction transaction, Bank destinationBank) {

        String thirdPartyUrl = destinationBank.getCreditImpactCallBackUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put(TRANSACTION_ID, transaction.getId());
        requestBody.put(TRANSACTION_REFERENCE,transaction.getTransRef());
        requestBody.put(AMOUNT,transaction.getAmount());
        requestBody.put(BENEFACTOR_ACCOUNT_NUMBER,transaction.getBenefactorAccountNumber());
        requestBody.put(BENEFACTOR_ACCOUNT_NAME,transaction.getBenefactorAccountName());
        requestBody.put(REMARK,transaction.getRemark());


        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        logger.info("Sending transaction impact notification to bank with code: "+transaction.getDestinationBankCode());

        return CompletableFuture.runAsync(() -> {
            ResponseEntity<String> response = restTemplate.postForEntity(thirdPartyUrl, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                transaction.setStatus(Status.SUCCESSFUL.getCode());
                logger.info("transaction impacted for transaction reference-code: "+transaction.getTransRef());
            } else {
                transaction.setStatus(Status.UNACKNOWLEDGED.getCode());
                logger.info("transaction un-impacted for transaction reference-code: "+transaction.getTransRef());
            }

            transactionRepository.save(transaction);
        });


    }
}
