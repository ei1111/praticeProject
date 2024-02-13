package notice.pratice.global.error.exception;

import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.dataResult.ErrorResMessAndCode;
import notice.pratice.global.error.exception.domainException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
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
    @ExceptionHandler(value = { UserValidException.class })
    protected ResponseEntity<ErrorResponse> userValidException(UserValidException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(value = { UserDuplicateException.class })
    protected ResponseEntity<ErrorResponse> userDuplicateException(UserDuplicateException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }
    @ExceptionHandler(value = JwtExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResMessAndCode jwtExpiredException(JwtExpiredException jwtExpiredException) {
        return new ErrorResMessAndCode(jwtExpiredException.getMessage(), jwtExpiredException.getResultCode());
    }

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     * 검증을 통과하지 못하면 bindException 예외
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<ErrorResponse> handleConflict(BusinessException e) {
        log.error("BusinessException", e);
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode().getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(errorResponse);
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
