package notice.pratice.global.error.exception.checkedException;

import lombok.Getter;

@Getter
public class DecodingException extends RuntimeException {
    private String resultCode;
    public DecodingException() {
    }

    public DecodingException(String message, String resultCode) {
        super(message);
        this.resultCode = resultCode;
    }
}
