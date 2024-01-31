package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
}