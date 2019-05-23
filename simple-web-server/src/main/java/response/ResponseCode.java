package response;

public enum ResponseCode {
    OK(200, "OK"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_FOUND(404, "Not Found");

    private int code;
    private String message;
    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return this.code; }
    public String getMessage() { return this.message; }
}
