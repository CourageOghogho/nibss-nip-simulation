package dot.ai.dotnibssmoc.dto;

import lombok.Data;

@Data
public class TransactionSearchParam extends QueryParam{
    private Long userId;
    private String sourceBankCode;
    private String benefactorBankCode;
    private String transRef;
}
