package notice.pratice.controller.exception;

import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.dataResult.ErrorResMessAndCode;
import notice.pratice.exception.checkedException.DecodingException;
import notice.pratice.exception.checkedException.EncodingException;
import notice.pratice.exception.checkedException.UnsupportedEncodingException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = UnsupportedEncodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResMessAndCode UnsupportedEncodingException(UnsupportedEncodingException unsupportedEncodingException) {
        return new ErrorResMessAndCode(unsupportedEncodingException.getMessage(), unsupportedEncodingException.getResultCode());
    }

    @ExceptionHandler(value = EncodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResMessAndCode EncodingException(EncodingException encodingException) {
        return new ErrorResMessAndCode(encodingException.getMessage(), encodingException.getResultCode());
    }

    @ExceptionHandler(value = DecodingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResMessAndCode DecodingException(DecodingException decodingException) {
        return new ErrorResMessAndCode(decodingException.getMessage(), decodingException.getResultCode());
    }
//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlaTExMTEiLCJkYXRhIjoiTXkgRmlyc3QgSldUICEhIiwiZXhwIjoxNzAxODQzMjkzfQ.sTqHu5-yVTCXBinwgHxgo46QVxv0PaLQ3Yw3rqgHcjc
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResMessAndCode RuntimeException(RuntimeException runtimeException, HttpServletRequest request){
        log.info("runtimeException.toString()={}", runtimeException.toString());
        log.info("request.getMethod={}" , request.getServletPath());
        log.info("request.getMethod={}" , request.getRequestURI());
        return new ErrorResMessAndCode("사용자 에러", String.valueOf(HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResMessAndCode Exception(Exception exception) {
        log.info("exception={}", exception);
        return new ErrorResMessAndCode(exception.getMessage(), String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
