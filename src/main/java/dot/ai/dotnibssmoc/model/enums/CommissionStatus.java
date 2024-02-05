package dot.ai.dotnibssmoc.model.enums;

public enum CommissionStatus {
    UNPROCESSED("1", "Commission is not computed yet"),
    PROCESSED("2","Commission value computed");
    private String code;
    private String description;

    CommissionStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
