package notice.pratice.global.error.exception.domainException;

import lombok.Getter;
import notice.pratice.global.error.exception.ErrorCode;
@Getter
public class LoginException extends RuntimeException {
    private ErrorCode errorCode;
    public LoginException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
