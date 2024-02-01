package gdsc3rdsc2.SignLanguageEducation.service;

import gdsc3rdsc2.SignLanguageEducation.domain.User;
import gdsc3rdsc2.SignLanguageEducation.repository.UserRepository;
import gdsc3rdsc2.SignLanguageEducation.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Value("${jwt.token.secret}")
    private String key;

    private Long expireTimeMs = 1000 * 60 * 60 * 24L;

    public String join(String userName, String password){

        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 회원입니다.");
                });

        User user = User.builder()
                .userName(userName)
                .password(password)
                .build();
        userRepository.save(user);
        return "SUCCESS";
    }

    public String login(String userName, String password){
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        if(selectedUser.getPassword() == password){
            throw new RuntimeException("비밀번호를 잘못 입력했습니다.");
        }

        return JwtTokenUtil.createToken(selectedUser.getUserName(),key,expireTimeMs);
    }
}
