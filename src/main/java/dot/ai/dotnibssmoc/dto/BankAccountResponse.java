package dot.ai.dotnibssmoc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankAccountResponse {
    private String accountName;
    private String accountNo;
    private String bankCode;
}
