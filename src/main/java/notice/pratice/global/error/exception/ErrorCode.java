package notice.pratice.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001","business exception test")
    ,USER_VALID(HttpStatus.INTERNAL_SERVER_ERROR, "-101","user valid excetion")
    ,USER_DUPLICATE(HttpStatus.INTERNAL_SERVER_ERROR, "-101","user duplicate exception");
    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
