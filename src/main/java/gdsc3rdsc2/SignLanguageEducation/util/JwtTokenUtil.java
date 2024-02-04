package gdsc3rdsc2.SignLanguageEducation.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


public class JwtTokenUtil {

    public static String getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userName",String.class);
    }

    public static boolean isValidToken(String token,String key) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch(Exception e) {
            throw new Exception("유효하지 않은 토큰입니다.");
        }
    }

    public static String createToken(String userName, String key, long expireTimeMs){
        Claims claims = Jwts.claims();
        claims.put("userName",userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .claim("userName",userName)
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }
}
