package gdsc3rdsc2.SignLanguageEducation.postconstruct;

import gdsc3rdsc2.SignLanguageEducation.domain.User;
import gdsc3rdsc2.SignLanguageEducation.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "userConstruct")
@DependsOn({"sentenceConstruct", "scriptConstruct"})
@RequiredArgsConstructor
@Slf4j
public class UserConstruct {
    private final UserRepository userRepository;

    @PostConstruct
    public void init(){
        log.info("UserConstruct init");

        User user = User.builder()
                .userName("admin")
                .password("admin")
                .scriptIds(List.of(1L,3L))
                .sentenceIds(List.of(2L))
                .build();

        userRepository.save(user);
    }

}
