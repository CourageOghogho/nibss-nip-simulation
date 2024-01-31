package dot.ai.dotnibssmoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferResponse {

    private String transactionReference;
    private String description;

}
