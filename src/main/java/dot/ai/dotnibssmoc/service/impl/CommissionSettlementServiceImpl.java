package dot.ai.dotnibssmoc.service.impl;

import dot.ai.dotnibssmoc.model.FinancialTransaction;
import dot.ai.dotnibssmoc.model.enums.CommissionStatus;
import dot.ai.dotnibssmoc.repository.TransactionRepository;
import dot.ai.dotnibssmoc.service.CommissionSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CommissionSettlementServiceImpl implements CommissionSettlementService {

    private final TransactionRepository transactionRepository;

    @Value("${commission.from.fee.percentage}")
    private Integer commissionPercentageOfFee;

    @Scheduled(cron = "0 0 0 * * ?") // Execute at midnight every day
    @Transactional
    public void processCommission() {
        List<FinancialTransaction> commissionWorthyTransactions = transactionRepository
                .findByIsCommissionWorthyTrueAndCommissionStatusIsNull();

        for (FinancialTransaction transaction : commissionWorthyTransactions) {
            BigDecimal commissionValue = calculateCommissionValue(transaction.getAmount());
            transaction.setCommissionValue(commissionValue);
            transaction.setCommissionStatus(CommissionStatus.PROCESSED.getCode());
            transaction.setIsCommissionWorthy(true);
            transactionRepository.save(transaction);
        }
    }

    private BigDecimal calculateCommissionValue(BigDecimal fee) {
        return  fee.multiply(BigDecimal.valueOf(commissionPercentageOfFee/100));
    }
}
