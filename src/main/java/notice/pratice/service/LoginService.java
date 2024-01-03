package notice.pratice.service;

import lombok.RequiredArgsConstructor;
import notice.pratice.domain.UseYn;
import notice.pratice.domain.dataResult.Message;
import notice.pratice.domain.dataResult.MessageToken;
import notice.pratice.domain.form.LoginForm;
import notice.pratice.entity.User;
import notice.pratice.entity.UserToken;
import notice.pratice.exception.domainException.LoginException;
import notice.pratice.repository.TokenRepository;
import notice.pratice.repository.UserRepository;
import notice.pratice.utils.AesClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    @Transactional
    public ResponseEntity<MessageToken> login(LoginForm loginForm, HttpServletRequest request) {
        ResponseEntity<MessageToken> result;

        //전달되는 데이터 암호화
        String id = AesClass.encrypt(loginForm.getWriterId());
        String pwd = AesClass.encrypt(loginForm.getWriterPwd());

        //회원가입 여부 확인
        User user = userRepository.selectUserCheck(id, pwd);

        if (!Objects.isNull(user)) {
            String refreshToekn = tokenService.saveToken(user, request);
            result = MessageToken.createMessage("로그인 완료", "1", HttpStatus.OK, refreshToekn);
        } else {
            throw new LoginException("존재하지 않는 아이디 입니다", "404");
        }
        return result;
    }

    @Transactional
    public ResponseEntity<Message> logout(String userId) {
        UserToken userToken = tokenRepository.findByTokenUser(userId);
        userToken.setUseYn(UseYn.N);

        return Message.createMessage("로그아웃 성공", "1", HttpStatus.OK);
    }
}
