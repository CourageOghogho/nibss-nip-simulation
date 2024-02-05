package dot.ai.dotnibssmoc.service;

import dot.ai.dotnibssmoc.dto.*;
import dot.ai.dotnibssmoc.model.FinancialTransaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransferResponse acceptTransfer(TransferRequest request);

    BankAccountResponse nameEnquiry(NameEnquiryRequest request);

    String transactionStatusEnquiry(String transactionReference);

    Page<FinancialTransaction> getTransactions(TransactionSearchParam queryParam);
}
