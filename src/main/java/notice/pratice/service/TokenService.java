package notice.pratice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.UseYn;
import notice.pratice.entity.User;
import notice.pratice.entity.UserToken;
import notice.pratice.repository.TokenRepository;
import notice.pratice.utils.AesClass;
import notice.pratice.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final TokenRepository tokenRepository;

    /*
    * 1. 로그인이 처음이라면 바로 토큰 저장
    * 2. 로그인 기록이 있다면 기존 데이터에 저장
    * */
    public String saveToken(User user, HttpServletRequest request) {
        //복호화 id
        String id = AesClass.decrypt(user.getWriterId());

        //엑세스 토큰
        String accessToken = JwtUtil.createToken(id, "0.5");
        //String accessToken = JwtUtil.createToken(id, "0.001");

        //리프레시 토큰
        String refreshToken = JwtUtil.createToken(id,"2");
       // String refreshToken = JwtUtil.createToken(id,"0.0002");

        //사용자 유저 정보
        String userAgent = request.getHeader("user-agent");

        UserToken userToken = tokenRepository.findByTokenUser(id);

        //token 조회가 없을 경우
        if (Objects.isNull(userToken)) {
            tokenRepository.save(
                    UserToken.builder()
                            .writerId(id)
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .userAgent(userAgent)
                            .build()
            );
        }else{
            userToken.setAccessToken(accessToken);
            userToken.setRefreshToken(refreshToken);
            userToken.setUseYn(UseYn.Y);
            userToken.setUserAgent(userAgent);
        }
        return refreshToken;
    }
}
