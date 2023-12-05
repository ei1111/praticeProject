package notice.pratice.domain.form;

import lombok.Getter;
import lombok.Setter;
import notice.pratice.entity.User;

@Getter
@Setter
public class UserForm {
    //userId
    private Integer id;
    //사용자 아이디
    private String writerId;
    //사용자 패스워드
    private String writerPwd;
    //사용자 이름
    private String writerName;

    public UserForm() {
    }
    public UserForm(User user){
        this.id = user.getId();
        this.writerId = user.getWriterId();
        this.writerPwd = user.getWriterPwd();
        this.writerName = user.getWriterName();
    }
}
