package dot.ai.dotnibssmoc.security;

import dot.ai.dotnibssmoc.model.Bank;
import dot.ai.dotnibssmoc.repository.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassportProvider {

    private  final BankRepository bankRepository;
    @Value("${source.bank.code}")
    private String sessionUserBankCode;

    public  Bank getSourceBank(){
        return bankRepository.findByBankCode(sessionUserBankCode).get();
    }
}
