package dot.ai.dotnibssmoc.model.enums;

public enum Status {
    PENDING("1","Pending"),
    SUCCESSFUL("2","Completed Successfully"),
    FAILED("3", "Failed transaction"),
    SENT("4", "Money is sent to Beneficiary's bank"),
    UNACKNOWLEDGED("5","Benefactor bank returned error");

    private String code;
    private String description;

    Status(String code, String description) {
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