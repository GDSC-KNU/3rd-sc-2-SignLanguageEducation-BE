package com.gdsc.rdsc2.SignLanguageEducation.service;

import com.gdsc.rdsc2.SignLanguageEducation.domain.User;
import com.gdsc.rdsc2.SignLanguageEducation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public String join(String userName, String password){

        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });

        User user = User.builder()
                .userName(userName)
                .password(password)
                .build();
        userRepository.save(user);
        return "SUCCESS";
    }
}
