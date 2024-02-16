package gdsc3rdsc2.SignLanguageEducation.service;

import gdsc3rdsc2.SignLanguageEducation.domain.User;
import gdsc3rdsc2.SignLanguageEducation.domain.dto.TokenResponse;
import gdsc3rdsc2.SignLanguageEducation.domain.dto.UserInfoResponse;
import gdsc3rdsc2.SignLanguageEducation.repository.UserRepository;
import gdsc3rdsc2.SignLanguageEducation.util.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String key;

    @Getter
    @Value("${jwt.token-validity-in-seconds}")
    private Long expireTimeMs;

    @Getter
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpireTimeMs;

    public void join(String userName, String password){

        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 회원입니다.");
                });

        User user = User.builder()
                .userName(userName)
                .password(password)
                .build();
        userRepository.save(user);
    }

    public TokenResponse login(String userName, String password){
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        if(!Objects.equals(selectedUser.getPassword(), password)){
            throw new RuntimeException("비밀번호를 잘못 입력했습니다.");
        }

        String accessToken = JwtTokenUtil.createAccessToken(selectedUser.getUserName(),key,expireTimeMs);
        String refreshToken = JwtTokenUtil.createRefreshToken(selectedUser.getUserName(),key,refreshTokenExpireTimeMs);

        selectedUser.updateRefreshToken(refreshToken);
        userRepository.save(selectedUser);

        return new TokenResponse(accessToken,refreshToken);
    }

    public void delete(String userName, String password){
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        if(!Objects.equals(selectedUser.getPassword(), password)){
            throw new RuntimeException("비밀번호를 잘못 입력했습니다.");
        }

        userRepository.delete(selectedUser);
    }

    public TokenResponse refresh(String refreshToken) {
        String userName = JwtTokenUtil.getUserName(refreshToken, key);
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        System.out.println(selectedUser.getRefreshToken() + " " + refreshToken);
        if(!Objects.equals(selectedUser.getRefreshToken(), refreshToken)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        String accessToken = JwtTokenUtil.createAccessToken(selectedUser.getUserName(),key,expireTimeMs);
        String newRefreshToken = JwtTokenUtil.createRefreshToken(selectedUser.getUserName(),key,refreshTokenExpireTimeMs);

        selectedUser.updateRefreshToken(newRefreshToken);

        return new TokenResponse(accessToken,newRefreshToken);
    }

    public Boolean idCheck(String id) {
        return userRepository.findByUserName(id).isPresent();
    }

    public UserInfoResponse info(String token) {
        String userName = JwtTokenUtil.getUserName(token,key);
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        return new UserInfoResponse(selectedUser.getId(),selectedUser.getUserName(),selectedUser.getPassword(),selectedUser.getScriptIds(),selectedUser.getSentenceIds());
    }
}
