package dot.ai.dotnibssmoc.security;

import dot.ai.dotnibssmoc.model.Bank;
import dot.ai.dotnibssmoc.model.User;
import dot.ai.dotnibssmoc.model.enums.UserRole;
import dot.ai.dotnibssmoc.repository.BankRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return bankRepository.findByBankCode(sessionUserBankCode).orElseThrow(
                ()-> new EntityNotFoundException("Bank record not found")
        );
    }

    public User getSessionUser(){
        // idealy, get the currently logged user from the auth/usermgn/pasport service as the case may be

        return new User(1,"courage.oghogho@gmail.com", UserRole.BANK);

    }
}
