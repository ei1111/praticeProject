package notice.pratice.exception.checkedException;

import lombok.Getter;

@Getter
public class EncodingException extends RuntimeException {
    private String resultCode;
    public EncodingException() {
    }

    public EncodingException(String message, String resultCode) {
        super(message);
        this.resultCode = resultCode;
    }
}
