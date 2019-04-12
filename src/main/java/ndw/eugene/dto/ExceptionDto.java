package ndw.eugene.dto;

public class ExceptionDto {
    private int code;
    private String reason;

    public ExceptionDto(int code, String reason) {

        this.code = code;
        this.reason = reason;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
