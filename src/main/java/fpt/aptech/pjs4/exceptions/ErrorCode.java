package fpt.aptech.pjs4.exceptions;

public enum ErrorCode {
    NOT_EXCEPTION(9999,"Loi khong xac dinh"),
    USER_EXITED(1001,"Tai khoan khong ton tai"),
    CHECK_EMAIL(1002,"Email da ton tai"),
    CHECK_PHONE(1003,"Sdt da ton tai"),
    CHECK_UPDATEPASS(1004,"Pass mới không giống pass cũ"),
    ENUM_KEYISWRONG(1006,"Enumkey da sai"),
    CHECK_LOGIN(1007,"Login that bai")

    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
