package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.dto.TransactionSearchParam;
import dot.ai.dotnibssmoc.dto.TransferRequest;
import dot.ai.dotnibssmoc.dto.TransferResponse;
import dot.ai.dotnibssmoc.model.Bank;
import dot.ai.dotnibssmoc.model.FinancialTransaction;
import dot.ai.dotnibssmoc.model.User;
import dot.ai.dotnibssmoc.model.Wallet;
import dot.ai.dotnibssmoc.model.enums.Status;
import dot.ai.dotnibssmoc.model.enums.UserRole;
import dot.ai.dotnibssmoc.repository.BankRepository;
import dot.ai.dotnibssmoc.repository.TransactionRepository;
import dot.ai.dotnibssmoc.repository.WalletRepository;
import dot.ai.dotnibssmoc.security.PassportProvider;
import dot.ai.dotnibssmoc.service.PlatformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {


    private WalletRepository walletRepository;


    private BankRepository bankRepository;


    private RestTemplate restTemplate;


    private TransactionRepository transactionRepository;


    private PassportProvider passportProvider;


    private PlatformService platformService;


    private TransactionServiceImpl transactionService;


    private Float transactionFeePercentage;

    private Float transactionFeeCapAt;

    @BeforeEach
    void setUp() {

        bankRepository= Mockito.mock(BankRepository.class);

        restTemplate= new RestTemplate();

        transactionRepository= Mockito.mock(TransactionRepository.class);

        walletRepository=Mockito.mock(WalletRepository.class);

        passportProvider=Mockito.mock(PassportProvider.class);

        platformService=Mockito.mock(PlatformService.class);

        transactionService=new TransactionServiceImpl(
                walletRepository,
                bankRepository,
                restTemplate,transactionRepository,
                passportProvider,
                platformService
        );

        ReflectionTestUtils.setField(transactionService, "transactionFeePercentage",
                0.5f
        );
        ReflectionTestUtils.setField(transactionService, "transactionFeeCapAt",
                100f
        );
    }

    @Test
    void acceptTransfer_WithValidRequest_ShouldReturnTransferResponse() {

        when(passportProvider.getSessionUser()).thenReturn(
                new User(1,"courage.oghogho@gmail.com", UserRole.BANK));
        // Arrange
        TransferRequest request = new TransferRequest();
        request.setAmount(new BigDecimal("100000.00"));
        request.setRemark("Sample transfer");
        request.setDestinationBankCode("ACCESS");
        request.setBenefactorAccountNumber("9876543210");
        request.setBenefactorAccountName("John Doe");
        request.setCreditorAccountName("Jane Doe");
        request.setCreditorAccountNumber("8765432109");


        Bank sourceBank =new Bank();
        sourceBank.setBankCode("PAGA");
        sourceBank.setName("Paga Microfinance");
        sourceBank.setUserId(1);
        sourceBank.setCreditImpactCallBackUrl("paga.com/credit/impact");
        Wallet wallet1=new Wallet();
        wallet1.setCommissionBalance(BigDecimal.ZERO);
        wallet1.setBalance(BigDecimal.valueOf(1230000000));
        sourceBank.setWallet(wallet1);
        wallet1.setBank(sourceBank);
        when(bankRepository.findByUserId(any())).thenReturn(Optional.of(sourceBank));
        when(walletRepository.findByBankCode(sourceBank.getBankCode())).thenReturn(Optional.of(wallet1));

        Bank bank2=new Bank();
        bank2.setBankCode("ACCESS");
        bank2.setName("Access Bank Plc");
        bank2.setUserId(2);
        bank2.setCreditImpactCallBackUrl("accessbank.com/credit/impact");
        Wallet wallet2=new Wallet();
        wallet2.setCommissionBalance(BigDecimal.ZERO);
        wallet2.setBalance(BigDecimal.valueOf(503000000));
        bank2.setWallet(wallet2);
        wallet2.setBank(bank2);
        when(bankRepository.findByBankCode("ACCESS")).thenReturn(Optional.of(bank2));

        when(walletRepository.findByBankCode(bank2.getBankCode())).thenReturn(Optional.of(wallet2));


        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setId(1);
        transaction.setCreditorAccountName("Jane Doe");
        transaction.setCreditorAccountNumber("8765432109");
        transaction.setSourceBankCode("PAGA");
        transaction.setBenefactorAccountName("John Doe");
        transaction.setBenefactorAccountNumber("9876543210");
        transaction.setDestinationBankCode("ACCESS");
        transaction.setStatus("4");
        transaction.setAmount(new BigDecimal("100000"));
        transaction.setTransRef("ac68560b-a0a0-4006-80a1-e4426caa56f3");
        transaction.setRemark("Sample transfer");
        transaction.setFee(new BigDecimal("100"));
        transaction.setIsCommissionWorthy(false);
        transaction.setCommissionStatus("1");
        when(transactionRepository.save(any(FinancialTransaction.class))).thenReturn(transaction);

        TransferResponse response = transactionService.acceptTransfer(request);

        assertNotNull(response);
        assertEquals(Status.SENT.getDescription(), response.getDescription());
    }


    @Test
    void transactionStatusEnquiry() {

        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setId(1);
        transaction.setCreditorAccountName("Jane Doe");
        transaction.setCreditorAccountNumber("8765432109");
        transaction.setSourceBankCode("PAGA");
        transaction.setBenefactorAccountName("John Doe");
        transaction.setBenefactorAccountNumber("9876543210");
        transaction.setDestinationBankCode("ACCESS");
        transaction.setStatus("4");
        transaction.setAmount(new BigDecimal("100000"));
        transaction.setTransRef("ac68560b-a0a0-4006-80a1-e4426caa56f3");
        transaction.setRemark("Sample transfer");
        transaction.setFee(new BigDecimal("100"));
        transaction.setIsCommissionWorthy(false);
        transaction.setCommissionStatus("1");
        when(transactionRepository.findByTransRef(any())).thenReturn(Optional.of(transaction));

        var response=transactionService.transactionStatusEnquiry("ac68560b-a0a0-4006-80a1-e4426caa56f3");

        assertEquals(transaction.getStatus(),response.getObject());

    }

    @Test
    void getTransactions() {
        TransactionSearchParam searchParam=new TransactionSearchParam();
        var pageable=searchParam.buildPageable();

        FinancialTransaction transaction = new FinancialTransaction();
        transaction.setId(1);
        transaction.setCreditorAccountName("Jane Doe");
        transaction.setCreditorAccountNumber("8765432109");
        transaction.setSourceBankCode("PAGA");
        transaction.setBenefactorAccountName("John Doe");
        transaction.setBenefactorAccountNumber("9876543210");
        transaction.setDestinationBankCode("ACCESS");
        transaction.setStatus("4");
        transaction.setAmount(new BigDecimal("100000"));
        transaction.setTransRef("ac68560b-a0a0-4006-80a1-e4426caa56f3");
        transaction.setRemark("Sample transfer");
        transaction.setFee(new BigDecimal("100"));
        transaction.setIsCommissionWorthy(false);
        transaction.setCommissionStatus("1");

        Bank sourceBank =new Bank();
        sourceBank.setBankCode("PAGA");
        sourceBank.setName("Paga Microfinance");
        sourceBank.setUserId(1);
        sourceBank.setCreditImpactCallBackUrl("paga.com/credit/impact");
        Wallet wallet1=new Wallet();
        wallet1.setCommissionBalance(BigDecimal.ZERO);
        wallet1.setBalance(BigDecimal.valueOf(1230000000));
        sourceBank.setWallet(wallet1);
        wallet1.setBank(sourceBank);
        when(bankRepository.findByUserId(any())).thenReturn(Optional.of(sourceBank));
        when(walletRepository.findByBankCode(sourceBank.getBankCode())).thenReturn(Optional.of(wallet1));

        when(passportProvider.getSessionUser()).thenReturn(
                new User(1,"courage.oghogho@gmail.com", UserRole.BANK));

        var responseObject= new PageImpl(Collections.singletonList(transaction),pageable,1);

        when(transactionRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(responseObject);

        var response=transactionService.searchTransactions(searchParam);
         assertEquals(response.getTotalElements(),1);
    }
}