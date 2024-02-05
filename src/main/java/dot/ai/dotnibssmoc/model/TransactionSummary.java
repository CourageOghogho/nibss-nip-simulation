package dot.ai.dotnibssmoc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionSummary extends BaseEntity{

    private Integer totalCount;
    private Integer successCount;
    private Integer pendingCount;
    private Integer totalFailed;
    private BigDecimal totalAmount;
    private BigDecimal totalFee;
    private LocalDate transactionDate;




}
