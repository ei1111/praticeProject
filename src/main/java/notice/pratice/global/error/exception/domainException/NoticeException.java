package notice.pratice.global.error.exception.domainException;

import lombok.Getter;

@Getter
public class NoticeException extends CommonException {


    public NoticeException(String message, String resultCode) {
        super(message, resultCode);
    }
}
