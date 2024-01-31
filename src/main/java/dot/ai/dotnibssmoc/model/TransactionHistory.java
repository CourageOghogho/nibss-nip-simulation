package dot.ai.dotnibssmoc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory extends BaseEntity{
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


    public  static TransactionHistory of(FinancialTransaction transaction){
        return TransactionHistory.builder()
                .creditorAccountName(transaction.getCreditorAccountName())
                .creditorAccountNumber(transaction.getCreditorAccountNumber())
                .sourceBankCode(transaction.getSourceBankCode())
                .benefactorAccountName(transaction.getBenefactorAccountName())
                .benefactorAccountNumber(transaction.getBenefactorAccountNumber())
                .isCommissionWorthy(transaction.getIsCommissionWorthy())
                .fee(transaction.getFee())
                .remark(transaction.getRemark())
                .transRef(transaction.getTransRef())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .destinationBankCode(transaction.getDestinationBankCode())
                .build();

    }


}
