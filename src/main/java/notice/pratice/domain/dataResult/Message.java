package notice.pratice.domain.dataResult;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;

@Getter
@Setter
public class Message {
    private String resultCode;
    private String message;

    public static ResponseEntity<Message> createMessage(String resultMessage,String resultCode , HttpStatus status) {
        Message message = new Message();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        message.setResultCode(resultCode);
        message.setMessage(resultMessage);

        return new ResponseEntity<>(message, headers, status);
    }
}
