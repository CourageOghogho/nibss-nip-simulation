package dot.ai.dotnibssmoc.repository;

import dot.ai.dotnibssmoc.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {
    Optional<Bank> findByBankCode(String sessionUserBankCode);

    Optional<Bank> findByUserId(Integer userId);
}