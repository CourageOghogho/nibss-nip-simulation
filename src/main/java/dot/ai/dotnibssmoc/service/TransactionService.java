package dot.ai.dotnibssmoc.service;

import dot.ai.dotnibssmoc.dto.BankAccountResponse;
import dot.ai.dotnibssmoc.dto.NameEnquiryRequest;
import dot.ai.dotnibssmoc.dto.TransferRequest;
import dot.ai.dotnibssmoc.dto.TransferResponse;

public interface TransactionService {
    TransferResponse acceptTransfer(TransferRequest request);

    BankAccountResponse nameEnquiry(NameEnquiryRequest request);

    String transactionStatusEnquiry(String transactionReference);
}
