package dot.ai.dotnibssmoc.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TransferRequest {
    private BigDecimal amount;
    private String remark;
    private String destinationBankCode;
    private String benefactorAccountNumber;
    private String benefactorAccountName;
    private String creditorAccountName;
    private String creditorAccountNumber;

}
