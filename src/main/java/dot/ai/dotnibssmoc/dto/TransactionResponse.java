package dot.ai.dotnibssmoc.dto;

import dot.ai.dotnibssmoc.model.FinancialTransaction;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class TransactionResponse {
    private String creditorAccountName;
    private String creditorAccountNumber;
    private String sourceBankCode;
    private String benefactorAccountName;
    private String benefactorAccountNumber;
    private String destinationBankCode;
    private String status;
    private BigDecimal amount;
    private String transRef;
    private String remark;

    public static TransactionResponse of(FinancialTransaction transaction){
        return TransactionResponse.builder()
                .creditorAccountName(transaction.getCreditorAccountName())
                .creditorAccountNumber(transaction.getCreditorAccountNumber())
                .benefactorAccountNumber(transaction.getBenefactorAccountNumber())
                .benefactorAccountName(transaction.getBenefactorAccountName())
                .sourceBankCode(transaction.getSourceBankCode())
                .destinationBankCode(transaction.getDestinationBankCode())
                .amount(transaction.getAmount())
                .transRef(transaction.getTransRef())
                .remark(transaction.getRemark())
                .status(transaction.getStatus())
                .build();
    }
}
