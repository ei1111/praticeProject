package notice.pratice.global.error.exception.domainException;

public class JwtExpiredException extends CommonException {
    public JwtExpiredException(String message, String resultCode) {
        super(message, resultCode);
    }
}
