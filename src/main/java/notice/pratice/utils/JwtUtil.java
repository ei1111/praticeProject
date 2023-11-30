package notice.pratice.utils;


import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    static final String key = "Bamdule";

    // 토큰 생성
    public static String createToken(String userId, String hour) {
        String jwt = null;
        // Header 부분 설정
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        // payload 부분 설정
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("data", "My First JWT !!");

        if(CheckType.isDouble(hour)){
            double doubleTime = Double.parseDouble(hour);
            int calculateExTime = (int)(1000 * 60 * 60 * doubleTime);
            Long expiredTime = Long.valueOf(calculateExTime); // 토큰 유효 시간

            Date ext = new Date(); // 토큰 만료 시간
            ext.setTime(ext.getTime() + expiredTime);

            // 토큰 Builder
            jwt = Jwts.builder().setHeader(headers) // Headers 설정
                    .setClaims(payloads) // Claims 설정
                    .setSubject(userId) // 토큰 용도
                    .setExpiration(ext) // 토큰 만료 시간 설정
                    .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과
                    // Key로 Sign
                    .compact(); // 토큰 생성
        }

        return jwt;
    }

    public static Boolean verifyJWT(String jwt)  {
        try {
            Claims claims = Jwts.parser().setSigningKey(key.getBytes("UTF-8")) // Set
                    // Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            return false;
        }catch (MalformedJwtException e){
            return false;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public static String resolveToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }

    public static String getUserId(String token) {
        String userId = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(key.getBytes()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        return userId;
    }
}
