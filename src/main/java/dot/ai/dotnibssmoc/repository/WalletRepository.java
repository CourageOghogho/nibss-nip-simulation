package dot.ai.dotnibssmoc.repository;

import dot.ai.dotnibssmoc.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    @Query("SELECT w FROM Wallet w WHERE w.bank.id = (SELECT b.id FROM Bank b WHERE b.bankCode = :bankCode)")
    Optional<Wallet> findByBankCode(@Param("bankCode") String bankCode);
}