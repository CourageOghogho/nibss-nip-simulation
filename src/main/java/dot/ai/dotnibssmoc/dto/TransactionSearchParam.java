package dot.ai.dotnibssmoc.dto;

import lombok.Data;

@Data
public class TransactionSearchParam extends QueryParam{
    private String sourceBankCode;
    private String benefactorBankCode;
    private String benefactorAccountName;
    private String benefactorAccountNumber;
    private String transRef;
    private String creditorName;
    private String creditorAccountNumber;
}
