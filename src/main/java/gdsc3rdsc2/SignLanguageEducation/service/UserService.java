package gdsc3rdsc2.SignLanguageEducation.service;

import gdsc3rdsc2.SignLanguageEducation.domain.User;
import gdsc3rdsc2.SignLanguageEducation.repository.UserRepository;
import gdsc3rdsc2.SignLanguageEducation.util.JwtTokenUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String key;

    @Getter
    @Value("${jwt.token-validity-in-seconds}")
    private Long expireTimeMs;

    public void join(String userName, String password){

        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new RuntimeException("이미 존재하는 회원입니다.");
                });

        User user = User.builder()
                .userName(userName)
                .password(password)
                .studyStatus(null)
                .build();
        userRepository.save(user);
    }

    public String login(String userName, String password){
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        if(!Objects.equals(selectedUser.getPassword(), password)){
            throw new RuntimeException("비밀번호를 잘못 입력했습니다.");
        }

        return JwtTokenUtil.createToken(selectedUser.getUserName(),key,expireTimeMs);
    }

    public void delete(String userName, String password){
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("해당하는 회원이 존재하지 않습니다."));

        if(!Objects.equals(selectedUser.getPassword(), password)){
            throw new RuntimeException("비밀번호를 잘못 입력했습니다.");
        }

        userRepository.delete(selectedUser);
    }
}
