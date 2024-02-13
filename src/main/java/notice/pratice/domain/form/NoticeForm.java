package notice.pratice.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import notice.pratice.entity.Notice;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@ApiModel(value="공지사항 입력 폼",description="제목, 내용, 비고를 가진 DomainClass")
public class NoticeForm {
    private Integer id;
    @NotEmpty(message = "제목은 필수 값 입니다.")
    @ApiModelProperty(value="제목")
    private String title;
    @NotEmpty(message = "내용은 필수 값 입니다.")
    @ApiModelProperty(value="내용")
    private String content;
    @ApiModelProperty(value="비고")
    private String note;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    public NoticeForm() {
    }

    public NoticeForm(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.note = notice.getNote();
        this.createAt = notice.getCreateAt();
    }
}
