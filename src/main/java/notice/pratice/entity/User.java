package notice.pratice.entity;

import lombok.*;
import notice.pratice.entity.baseEntity.BaseTimeEntity;
import javax.persistence.*;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {
    //userId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //사용자 아이디
    private String writerId;
    //사용자 패스워드
    private String writerPwd;
    //사용자 이름
    private String writerName;


    @Builder
    public User(Integer id, String writerId, String writerPwd, String writerName) {
        this.id = id;
        this.writerId = writerId;
        this.writerPwd = writerPwd;
        this.writerName = writerName;
    }
}
