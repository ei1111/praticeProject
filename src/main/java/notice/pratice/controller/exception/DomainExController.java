package notice.pratice.controller.exception;

import notice.pratice.domain.dataResult.ErrorResMessAndCode;
import notice.pratice.exception.domainException.JwtExpiredException;
import notice.pratice.exception.domainException.LoginException;
import notice.pratice.exception.domainException.LogoutException;
import notice.pratice.exception.domainException.NoticeException;
import notice.pratice.exception.domainException.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DomainExController {
    @ExceptionHandler(value = NoticeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResMessAndCode noticeException(NoticeException noticeException) {
        return new ErrorResMessAndCode(noticeException.getMessage(), noticeException.getResultCode());
    }

    @ExceptionHandler(value = LoginException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResMessAndCode loginException(LoginException loginException) {
        return new ErrorResMessAndCode(loginException.getMessage(), loginException.getResultCode());
    }

    @ExceptionHandler(value = LogoutException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResMessAndCode logoutException(LogoutException logoutException) {
        return new ErrorResMessAndCode(logoutException.getMessage(), logoutException.getResultCode());
    }

    @ExceptionHandler(value = UserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResMessAndCode userException(UserException userException) {
        return new ErrorResMessAndCode(userException.getMessage(), userException.getResultCode());
    }

    @ExceptionHandler(value = JwtExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResMessAndCode jwtExpiredException(JwtExpiredException jwtExpiredException) {
        return new ErrorResMessAndCode(jwtExpiredException.getMessage(), jwtExpiredException.getResultCode());
    }
}
