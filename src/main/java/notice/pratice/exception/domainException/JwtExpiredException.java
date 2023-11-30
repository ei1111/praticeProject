package notice.pratice.exception.domainException;

import notice.pratice.exception.domainException.CommonException;

public class JwtExpiredException extends CommonException {
    public JwtExpiredException(String message, String resultCode) {
        super(message, resultCode);
    }
}
