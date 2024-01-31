package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.dto.BankAccountResponse;
import dot.ai.dotnibssmoc.dto.NameEnquiryRequest;
import dot.ai.dotnibssmoc.service.PlatformService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlatformServiceImpl implements PlatformService {
    @Value("${mock.account.name}")
    private String mockedAccountName;
    @Value("${mock.account.number}")
    private String mockedAccountNo;
    @Value("${mock.bank.code}")
    private String mockedBankCode;
    @Override
    public BankAccountResponse nameEnquiry(NameEnquiryRequest request) {
        //ideally
        //crate and send http request to the central database nibss for the account
        //I am mocking this for scope of this task

        if(request.getAccountNumber().equals(mockedAccountNo)&& request.getBankCode().equals(mockedBankCode)){
            return BankAccountResponse.builder()
                    .accountName(mockedAccountName)
                    .accountNo(mockedAccountNo)
                    .bankCode(mockedBankCode)
                    .build();
        }
        throw  new EntityNotFoundException("RECORD NOT FOUND");

    }
}
