package notice.pratice.domain.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ApiModel(value="로그인 폼",description="사용자아이디, 비밀번호을 가진 DomainClass")
public class LoginForm {
    //사용자 아이디
    @ApiModelProperty(value="아이디" , example = "ei1111")
    @NotEmpty(message = "아이디는 필수 입니다.")
    private String writerId;
    //사용자 패스워드
    @ApiModelProperty(value="비밀번호" , example = "1234")
    @NotEmpty(message = "비밀번호는 필수 입니다.")
    private String writerPwd;
}
