package notice.pratice.global.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    TEST(HttpStatus.INTERNAL_SERVER_ERROR, "001","business exception test")
    ,USER_VALID(HttpStatus.NOT_FOUND, "-101","user valid excetion")
    ,NOT_EXIST_USER(HttpStatus.NOT_FOUND, "404","user not exist excetion")
    ,USER_DUPLICATE(HttpStatus.NOT_FOUND, "-101","user duplicate exception");
    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
