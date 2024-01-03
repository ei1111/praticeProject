package notice.pratice.domain.dataResult;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import java.nio.charset.Charset;

@Getter
@Setter
public class MessageToken {
    private String resultCode;
    private String message;
    private String token;

    public static ResponseEntity<MessageToken> createMessage(String resultMessage, String resultCode, HttpStatus status, String refreshToken) {
        MessageToken message = new MessageToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setResultCode(resultCode);
        message.setMessage(resultMessage);
        message.setToken(refreshToken);

        return new ResponseEntity<>(message, headers, status);
    }
}
