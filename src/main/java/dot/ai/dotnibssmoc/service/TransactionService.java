package dot.ai.dotnibssmoc.service;

import dot.ai.dotnibssmoc.dto.*;
import dot.ai.dotnibssmoc.model.FinancialTransaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransferResponse acceptTransfer(TransferRequest request);

    BankAccountResponse nameEnquiry(NameEnquiryRequest request);

    ApiResponse transactionStatusEnquiry(String transactionReference);

    Page<TransactionResponse> searchTransactions(TransactionSearchParam searchParam);
}
