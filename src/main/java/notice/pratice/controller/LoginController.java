package notice.pratice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.form.LoginForm;
import notice.pratice.service.LoginService;
import notice.pratice.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Api(tags = {"1. 로그인 및 로그아웃"})
@RequestMapping("/auths")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "로그인 기능" , notes = "DB에 등록된 ID는 로그인 할 수 있습니다.")
    public ResponseEntity<Message> login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        return loginService.login(loginForm, request);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "로그 아웃 기능" , notes = "DB에 등록된 ID는 토근 확인 후 로그 아웃 할 수 있습니다.")
    public ResponseEntity<Message> logout(HttpServletRequest request) {
        String userId = JwtUtil.getUserId(JwtUtil.resolveToken(request));
        return loginService.logout(userId);
    }
}
