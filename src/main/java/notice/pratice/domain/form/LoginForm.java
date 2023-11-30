package notice.pratice.domain.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    //사용자 아이디
    private String writerId;
    //사용자 패스워드
    private String writerPwd;
}
