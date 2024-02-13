package notice.pratice.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.dataResult.MessageToken;
import notice.pratice.domain.form.LoginForm;
import notice.pratice.global.error.exception.ErrorCode;
import notice.pratice.global.error.exception.domainException.UserValidException;
import notice.pratice.service.LoginService;
import notice.pratice.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = {"1. 로그인 및 로그아웃"})
@RequestMapping("/auths")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인 기능" , notes = "DB에 등록된 ID는 로그인 할 수 있습니다.")
    @ApiResponses({@ApiResponse(code=200,message="조회완료"),@ApiResponse(code=-101,message="가입 폼 누락"),@ApiResponse(code=-103,message="토큰에러") ,@ApiResponse(code=500,message="서버문제발생")})
    public ResponseEntity<MessageToken> login(@RequestBody @Valid  @ApiParam(value="writerId: 사용자 아이디\n" + "writerPwd: 사용자 패스워드" ,required=true) LoginForm loginForm, BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            throw new UserValidException(ErrorCode.USER_VALID);
        }
        return loginService.login(loginForm, request);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "로그 아웃 기능" , notes = "DB에 등록된 ID는 토근 확인 후 로그 아웃 할 수 있습니다.")
    public ResponseEntity<Message> logout(HttpServletRequest request) {
        String userId = JwtUtil.getUserId(JwtUtil.resolveToken(request));
        return loginService.logout(userId);
    }
}
