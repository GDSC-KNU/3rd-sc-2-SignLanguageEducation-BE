package gdsc3rdsc2.SignLanguageEducation.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTokenUtil {

    public static String getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().get("userName",String.class);
    }

    public static boolean isValidToken(String token,String key) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch(ExpiredJwtException e) {
            throw new Exception("만료된 토큰입니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("유효하지 않은 토큰입니다.");
        }
    }

    public static String createAccessToken(String userName, String key, long expireTimeMs){
        Claims claims = Jwts.claims();
        claims.put("userName",userName);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .claim("userName",userName)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return accessToken;
    }

    public static String createRefreshToken(String userName, String key, Long refreshTokenExpireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("userName",userName);
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireTimeMs))
                .claim("userName",userName)
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return refreshToken;
    }
}
