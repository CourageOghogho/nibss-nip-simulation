package dot.ai.dotnibssmoc.repository;

import dot.ai.dotnibssmoc.model.FinancialTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<FinancialTransaction, Integer> {
    Optional<FinancialTransaction> findByTransRef(String transactionReference);
}