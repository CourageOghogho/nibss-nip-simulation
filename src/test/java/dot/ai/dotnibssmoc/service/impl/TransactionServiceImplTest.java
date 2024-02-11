package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.dto.TransactionSearchParam;
import dot.ai.dotnibssmoc.dto.TransferRequest;
import dot.ai.dotnibssmoc.dto.TransferResponse;
import dot.ai.dotnibssmoc.model.Bank;
import dot.ai.dotnibssmoc.model.FinancialTransaction;
import dot.ai.dotnibssmoc.model.enums.Status;
import dot.ai.dotnibssmoc.repository.BankRepository;
import dot.ai.dotnibssmoc.repository.TransactionRepository;
import dot.ai.dotnibssmoc.repository.WalletRepository;
import dot.ai.dotnibssmoc.security.PassportProvider;
import dot.ai.dotnibssmoc.service.PlatformService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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

    }

    @Test
    void acceptTransfer_WithValidRequest_ShouldReturnTransferResponse() {
        // Arrange
        TransferRequest request = new TransferRequest();
        request.setAmount(BigDecimal.TEN);
        request.setRemark("Test transfer");
        request.setDestinationBankCode("DEST_BANK_CODE");

        Bank sourceBank = new Bank();
        sourceBank.setBankCode("SOURCE_BANK_CODE");
        when(passportProvider.getSourceBank()).thenReturn(sourceBank);

        Bank destinationBank = new Bank();
        destinationBank.setBankCode("DEST_BANK_CODE");
        when(bankRepository.findByBankCode("DEST_BANK_CODE")).thenReturn(Optional.of(destinationBank));

        FinancialTransaction transaction = new FinancialTransaction();
        when(transactionRepository.save(any(FinancialTransaction.class))).thenReturn(transaction);

        // Act
        TransferResponse response = transactionService.acceptTransfer(request);

        // Assert
        assertNotNull(response);
        assertEquals(Status.SENT.getDescription(), response.getDescription());
    }

    @Test
    void getTransactions_WithValidQueryParam_ShouldReturnPageOfTransactions() {
        // Arrange
//        TransactionSearchParam queryParam = new TransactionSearchParam();
//        queryParam.setStatus("SUCCESS");
//        when(transactionRepository.findAll(any(), any())).thenReturn(Page.empty());
//
//        // Act
//        transactionService.getTransactions(queryParam);
//
//        // Assert
//        verify(transactionRepository, times(1)).findAll(any(), any());
    }



    @Test
    void nameEnquiry() {
    }

    @Test
    void transactionStatusEnquiry() {
    }

    @Test
    void acceptTransfer() {
    }

    @Test
    void getTransactions() {
    }
}