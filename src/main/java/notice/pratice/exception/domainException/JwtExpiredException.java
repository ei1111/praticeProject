package notice.pratice.exception.domainException;

public class JwtExpiredException extends CommonException {
    public JwtExpiredException(String message, String resultCode) {
        super(message, resultCode);
    }
}
