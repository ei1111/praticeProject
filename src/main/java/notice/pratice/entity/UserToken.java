package notice.pratice.entity;

import lombok.*;
import notice.pratice.domain.UseYn;
import notice.pratice.entity.baseEntity.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserToken extends BaseTimeEntity {
    //토큰 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq;
    //사용자 아이디
    private String writerId;
    //엑세스 토큰
    private String accessToken;
    //리프레시 토큰
    private String refreshToken;
    //사용여부
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'Y'") // default
    private UseYn useYn;
    //사용자 에이전트
    private String userAgent;

    @Builder
    public UserToken(Integer seq, String writerId, String accessToken, String refreshToken, UseYn useYn, String userAgent) {
        this.seq = seq;
        this.writerId = writerId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.useYn = useYn;
        this.userAgent = userAgent;
    }
}
