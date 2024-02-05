package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.dto.QueryParam;
import dot.ai.dotnibssmoc.model.TransactionSummary;
import dot.ai.dotnibssmoc.model.enums.Status;
import dot.ai.dotnibssmoc.exceptions.BadRequestException;
import dot.ai.dotnibssmoc.repository.TransactionRepository;
import dot.ai.dotnibssmoc.repository.TransactionSummaryRepository;
import dot.ai.dotnibssmoc.service.TransactionHistoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TransactionSummaryServiceImpl implements TransactionHistoryService {
    private final TransactionSummaryRepository transactionSummaryRepository;
    private final TransactionRepository transactionRepository;


    @Override
    public void create(TransactionSummary history) {
        transactionSummaryRepository.save(history);

    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void calculateAndStoreDailySummary() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        TransactionSummary summary = getTransactionSummary(yesterday);
        transactionSummaryRepository.save(summary);
    }


    private TransactionSummary getTransactionSummary(LocalDate date) {
        return transactionRepository.calculateTransactionSummary(
                date,
                Status.SUCCESSFUL.getCode(),
                Status.PENDING.getCode(),
                Status.FAILED.getCode()).orElseThrow(
                ()-> new EntityNotFoundException("Transaction history not found")
        );
    }

    @Override
    public Page<TransactionSummary> getTransactionSummaryByDateRange(QueryParam queryParam) {
        if(queryParam.getStartDate().toLocalDate().isEqual(LocalDate.now())){
            throw new BadRequestException("History for today is not ready");
        }
        Pageable pageable = queryParam.buildPageable();
        return transactionSummaryRepository.findByTransactionDateBetween(
                queryParam.getStartDate().toLocalDate(),
                queryParam.getEndDate().toLocalDate(),
                pageable
        );
    }


}