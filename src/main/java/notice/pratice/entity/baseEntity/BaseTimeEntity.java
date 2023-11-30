package notice.pratice.entity.baseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    //생성시간
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createAt;

    //수정시간
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
