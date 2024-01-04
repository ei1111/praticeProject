package notice.pratice.entity;

import lombok.*;
import notice.pratice.entity.baseEntity.BaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity{
    //noticeId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //제목
    private String title;
    //내용
    private String content;
    //비고
    private String note;

    @Builder
    public Notice(Integer id, String title, String content, String note) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.note = note;
    }
}
