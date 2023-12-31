package notice.pratice.entity.baseEntity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends BaseTimeEntity{

    //생성자
    @CreatedBy
    @Column(updatable = false)
    private String createWriterId;

    //수정자
    @LastModifiedBy
    private String modifiedWriterId;
}
