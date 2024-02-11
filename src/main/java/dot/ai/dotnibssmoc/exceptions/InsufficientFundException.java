package dot.ai.dotnibssmoc.exceptions;

import lombok.Data;

@Data
public class InsufficientFundException extends RuntimeException {
    private String description;
    public InsufficientFundException(String description) {
        super(description);
        this.description=description;
    }
}
