package dot.ai.dotnibssmoc.exceptions;

import lombok.Data;

@Data
public class BadRequestException extends RuntimeException{
    private String description;

    public BadRequestException(String message) {
        super(message);
        this.description = message;
    }
}
