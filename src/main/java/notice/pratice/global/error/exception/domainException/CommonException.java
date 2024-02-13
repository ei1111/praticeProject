package notice.pratice.global.error.exception.domainException;

import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{
    private String resultCode;

    public CommonException(String message, String resultCode) {
        super(message);
        this.resultCode = resultCode;
    }
}
