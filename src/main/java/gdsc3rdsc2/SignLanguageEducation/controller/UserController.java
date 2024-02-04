package gdsc3rdsc2.SignLanguageEducation.controller;

import gdsc3rdsc2.SignLanguageEducation.domain.dto.UserDeleteRequest;
import gdsc3rdsc2.SignLanguageEducation.domain.dto.UserJoinRequest;
import gdsc3rdsc2.SignLanguageEducation.domain.dto.UserLoginRequest;
import gdsc3rdsc2.SignLanguageEducation.service.UserService;
import gdsc3rdsc2.SignLanguageEducation.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){
        userService.join(dto.getUserName(),dto.getPassword());

        return ResponseEntity.ok().body("회원가입을 성공적으로 완료했습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> log(@RequestBody UserLoginRequest dto){
        String token = userService.login(dto.getUserName(),dto.getPassword());
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody UserDeleteRequest dto){
        userService.delete(dto.getUserName(),dto.getPassword());
        return ResponseEntity.ok().body("회원탈퇴를 성공적으로 완료했습니다.");
    }

    @PostMapping("/edit")
    public ResponseEntity<String> edit(@RequestBody UserLoginRequest dto){
        return ResponseEntity.ok().body("회원정보를 수정했습니다.");
    }

//    @PostMapping("/test")
//    public ResponseEntity<String> a(@RequestBody UserLoginRequest dto){
//        String token = userService.login(dto.getUserName(),dto.getPassword());
//        return ResponseEntity.ok().body("안녕하세요 " + JwtTokenUtil.getUserName(token,"c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK") + "님");
//    }
}
