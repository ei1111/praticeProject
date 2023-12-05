package notice.pratice.controller;

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
@RequestMapping("/auth")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        return loginService.login(loginForm, request);
    }

    @GetMapping("/logout")
    public ResponseEntity<Message> logout(HttpServletRequest request) {
        String userId = JwtUtil.getUserId(JwtUtil.resolveToken(request));
        return loginService.logout(userId);
    }
}
