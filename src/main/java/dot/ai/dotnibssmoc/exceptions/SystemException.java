package dot.ai.dotnibssmoc.exceptions;

import lombok.Data;

@Data
public class SystemException extends RuntimeException{
    private String description;

    public SystemException(String description) {
        super(description);
        this.description = description;
    }
}
