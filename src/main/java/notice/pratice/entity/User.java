package notice.pratice.entity;

import lombok.*;
import notice.pratice.entity.baseEntity.BaseTimeEntity;
import javax.persistence.*;

@Getter
@Entity
@ToString
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    //userId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //사용자 아이디
    @Column(columnDefinition = "varchar2(256)")
    private String writerId;
    //사용자 패스워드
    @Column(columnDefinition = "varchar2(256)")
    private String writerPwd;
    //사용자 이름
    @Column(columnDefinition = "varchar2(256)")
    private String writerName;


    @Builder
    public User(Integer id, String writerId, String writerPwd, String writerName) {
        this.id = id;
        this.writerId = writerId;
        this.writerPwd = writerPwd;
        this.writerName = writerName;
    }
}
