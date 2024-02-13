package notice.pratice.global.error.exception.checkedException;

import lombok.Getter;

@Getter
public class UnsupportedEncodingException extends RuntimeException{
    private String resultCode;
    public UnsupportedEncodingException() {
    }

    public UnsupportedEncodingException(String message, String resultCode) {
        super(message);
        this.resultCode = resultCode;
    }
}
