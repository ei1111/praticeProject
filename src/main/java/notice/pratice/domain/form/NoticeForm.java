package notice.pratice.domain.form;

import lombok.Getter;
import notice.pratice.entity.Notice;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
public class NoticeForm {
    private Integer id;
    @NotEmpty(message = "제목은 필수 값 입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수 값 입니다.")
    private String content;
    private String note;
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
