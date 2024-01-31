package dot.ai.dotnibssmoc.service;

import dot.ai.dotnibssmoc.dto.BankAccountResponse;
import dot.ai.dotnibssmoc.dto.NameEnquiryRequest;

public interface PlatformService {
    BankAccountResponse nameEnquiry(NameEnquiryRequest request);
}
