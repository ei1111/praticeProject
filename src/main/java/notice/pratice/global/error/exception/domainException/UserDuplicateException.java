package notice.pratice.global.error.exception.domainException;

import lombok.Getter;
import notice.pratice.global.error.exception.ErrorCode;

@Getter
public class UserDuplicateException extends RuntimeException {
    private ErrorCode errorCode;
    public UserDuplicateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
