package notice.pratice.config;

import lombok.extern.slf4j.Slf4j;
import notice.pratice.domain.UseYn;
import notice.pratice.entity.UserToken;
import notice.pratice.exception.domainException.JwtExpiredException;
import notice.pratice.exception.domainException.LogoutException;
import notice.pratice.repository.TokenRepository;
import notice.pratice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class JoinCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenRepository tokenRepository;

    /*
     * 로그인 페이지는 jwt 검증을 하지 않는다.
     * 그 외의 나머지 페이지는 jwt 검증을 한다.
     *  - accessToken 만료시간은 30분, refreshToken 만료시간은 2시간으로 설정한다.
     *  - accessToken가 만료되고 refreshToken 토큰이 만료되지 않았다면 refreshToken을 사용하여 토큰을 재발행한다.
     *  - accessToken과 refreshToken이 만료되면 접근이 불가능 하다는 응답을 보여준다.
     * */
    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = JwtUtil.resolveToken(request);
        //오류 검증
        //jwt 없이 접근시
        if (!StringUtils.hasText(jwt)) {
            throw new JwtExpiredException("토큰 없이 접근이 불가능합니다", "-103");
        }

        return tokenCheck(jwt, request, response);
    }

    /*
     * accessToken인지 refreshToken 구별하기
     * 1.aceessToken인지 refreshToken인지 조회
     *
     * accessToken
     *   -accessToken 만료되었고 refreshToken 만료되지 않았다면 재발행
     *
     * refreshToken일때
     *  -refreshToken일때 만료되었으면 redirect 처리
     * */
    public Boolean tokenCheck(String jwt, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Boolean result = true;

        UserToken accessUserToken = tokenRepository.findByAccessToken(jwt);
        UserToken refreshUserToken = tokenRepository.findByRefreshToken(jwt);

        boolean accessTokenbool = Objects.isNull(accessUserToken);
        boolean refreshTokenbool = Objects.isNull(refreshUserToken);


        //accessToken일 경우
        if (!accessTokenbool) {
            UseYn useYn = accessUserToken.getUseYn();
            //로그아웃 체크
            checkLogout(useYn);

            String accessToken = accessUserToken.getAccessToken();
            String refreshToken = accessUserToken.getRefreshToken();

            boolean accessTokenVerify = JwtUtil.verifyJWT(accessToken);
            boolean refreshTokenVerify = JwtUtil.verifyJWT(refreshToken);

            //토큰 두개가 만료 되었을때
            if (!accessTokenVerify && !refreshTokenVerify) {
                throw new JwtExpiredException("토큰 오류", "-103");
                //accessToken은 만료되고 refreshToken은 만료되지 않았을 경우
            } else if (accessTokenVerify == false && refreshTokenVerify == true) {
                //엑세스 토큰
                String atoken = JwtUtil.createToken(accessUserToken.getWriterId(), "0.5");

                //리프레시 토큰
                String rtoken = JwtUtil.createToken(accessUserToken.getWriterId(), "2");

                //사용자 환경
                String userAgent = request.getHeader("user-agent");

                //사용자 id
                String userId = JwtUtil.getUserId(refreshToken);

                //사용자 유저 정보
                accessUserToken.setAccessToken(atoken);
                accessUserToken.setRefreshToken(rtoken);
                accessUserToken.setUserAgent(userAgent);

                request.setAttribute("userId", userId);
            }
            //refreshToken일 경우
        } else if (!refreshTokenbool) {
            UseYn useYn = refreshUserToken.getUseYn();

            String refreshToken = refreshUserToken.getRefreshToken();
            Boolean refreshTokenBool = JwtUtil.verifyJWT(refreshToken);
            //로그아웃 체크
            checkLogout(useYn);
            //refreshToken이 만료되었을 경우
            if (!refreshTokenBool) {
                throw new JwtExpiredException("토큰 오류", "-103");
            }
        } else {
            //조회시 데이터가 둘 다 없을 경우 이상한 토큰
            if (accessTokenbool && refreshTokenbool) {
                throw new JwtExpiredException("토큰 오류", "-103");
            }

        }
        return result;
    }

    private static void checkLogout(UseYn useYn) {
        if (useYn == UseYn.N) {
            throw new LogoutException("로그아웃 되었습니다. 다시 로그인 해주세요", "404");
        }
    }
}
