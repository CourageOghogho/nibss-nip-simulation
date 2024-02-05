package dot.ai.dotnibssmoc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class FinancialTransaction extends BaseEntity{
    private String creditorAccountName;
    private String creditorAccountNumber;
    private String sourceBankCode;
    private String benefactorAccountName;
    private String benefactorAccountNumber;
    private String destinationBankCode;
    private String status;
    private BigDecimal amount;
    @Column(unique = true)
    private String transRef;
    private String remark;
    private BigDecimal fee;
    private Boolean isCommissionWorthy;
    private String commissionStatus;
    private BigDecimal commissionValue;


}
