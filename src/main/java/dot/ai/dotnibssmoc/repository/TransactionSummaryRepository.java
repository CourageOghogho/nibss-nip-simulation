package dot.ai.dotnibssmoc.repository;

import dot.ai.dotnibssmoc.model.TransactionSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface TransactionSummaryRepository extends JpaRepository<TransactionSummary, Integer> {
    Optional<TransactionSummary> findByTransRef(String transRef);

    Page<TransactionSummary> findByTransactionDateBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

}