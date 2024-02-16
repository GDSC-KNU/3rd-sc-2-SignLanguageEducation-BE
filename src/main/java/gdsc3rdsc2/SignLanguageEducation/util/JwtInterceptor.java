package gdsc3rdsc2.SignLanguageEducation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String key;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String token = authorization.replaceAll("Bearer ", "");

        if(token.length() > 10){
            if(JwtTokenUtil.isValidToken(token,key)){
                ObjectMapper objectMapper = new ObjectMapper();

                String userName = objectMapper.writeValueAsString(JwtTokenUtil.getUserName(token, key));

                request.setAttribute("userName", userName);
                return true;
            }
        } else {
            throw new RuntimeException("인증 토큰이 없습니다.");
        }
        return false;


    }
}
