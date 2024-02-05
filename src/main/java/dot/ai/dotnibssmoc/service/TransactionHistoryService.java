package dot.ai.dotnibssmoc.service;

import dot.ai.dotnibssmoc.model.TransactionSummary;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TransactionHistoryService {
    void create(TransactionSummary history);
    Page<TransactionSummary> getTransactionSummaryByDateRange(LocalDate fromDate, LocalDate toDate, int pageNo, int pageSize);
}
