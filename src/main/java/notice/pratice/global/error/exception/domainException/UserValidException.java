package notice.pratice.global.error.exception.domainException;

import lombok.Getter;
import notice.pratice.global.error.exception.ErrorCode;

@Getter
public class UserValidException extends RuntimeException {
    private ErrorCode errorCode;
    public UserValidException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
