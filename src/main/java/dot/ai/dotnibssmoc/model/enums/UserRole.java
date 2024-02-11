package dot.ai.dotnibssmoc.model.enums;

import java.util.Optional;
import java.util.stream.Stream;

public enum UserRole {
    ADMIN("1","System Admin"),
    BANK("2","Banking institution");

    private String code;
    private String description;

    UserRole(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    public static String getDescriptionOrDefault(String code, String defaultName) {
        return Stream.of(values()).filter(value -> value.code.equalsIgnoreCase(code))
                .findFirst()
                .map(value -> value.description)
                .orElse(defaultName);
    }

    public static Optional<UserRole> get(String code) {
        return Stream.of(values()).filter(v -> v.code.equalsIgnoreCase(code))
                .findFirst();
    }
}
