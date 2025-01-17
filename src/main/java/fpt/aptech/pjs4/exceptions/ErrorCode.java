package fpt.aptech.pjs4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    NOT_EXCEPTION(9999,"Loi khong xac dinh", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXITED(1001,"Tai khoan khong ton tai", HttpStatus.BAD_REQUEST),
    CHECK_EMAIL(1002,"Email da ton tai", HttpStatus.BAD_REQUEST),
    CHECK_PHONE(1003,"Sdt da ton tai", HttpStatus.BAD_REQUEST),
    CHECK_UPDATEPASS(1004,"Pass mới không giống pass cũ",HttpStatus.BAD_REQUEST),
    ENUM_KEYISWRONG(1006,"Enumkey da sai",HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1007,"unauthenticated _ khong duoc xac thuc",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008,"Dont't permisstion",HttpStatus.FORBIDDEN),

    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    ErrorCode(int code, String message,HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
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
