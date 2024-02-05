package dot.ai.dotnibssmoc.repository;

import dot.ai.dotnibssmoc.model.FinancialTransaction;
import dot.ai.dotnibssmoc.model.TransactionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<FinancialTransaction, Integer>, JpaSpecificationExecutor<FinancialTransaction> {
    Optional<FinancialTransaction> findByTransRef(String transactionReference);

    List<FinancialTransaction> findByIsCommissionWorthyTrueAndCommissionStatusIsNull();

    @Query("SELECT " +
            "COUNT(t.id) AS totalCount, " +
            "SUM(CASE WHEN t.status = :successCode THEN 1 ELSE 0 END) AS successCount, " +
            "SUM(CASE WHEN t.status = :pendingCode THEN 1 ELSE 0 END) AS pendingCount, " +
            "SUM(CASE WHEN t.status = :failedCode THEN 1 ELSE 0 END) AS totalFailed, " +
            "SUM(t.amount) AS totalAmount, " +
            "SUM(t.fee) AS totalFee " +
            "FROM FinancialTransaction t " +
            "WHERE t.createdAt = :date")
    Optional<TransactionSummary> calculateTransactionSummary(
            @Param("date") LocalDate date,
            @Param("successCode") String successCode,
            @Param("pendingCode") String pendingCode,
            @Param("failedCode") String failedCode
    );
}