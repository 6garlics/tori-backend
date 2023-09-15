package com.site.bemystory.controller;

import com.site.bemystory.domain.User;
import com.site.bemystory.dto.*;
import com.site.bemystory.service.FollowService;
import com.site.bemystory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/users/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) {

        userService.join(dto.getUserName(), dto.getPassword(), dto.getEmail());
        log.info("{}님 회원가입 성공", dto.getUserName());
        return ResponseEntity.ok().body("회원가입 성공했습니다.");
    }

    /**
     * 로그인
     */
    @PostMapping("/users/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginRequest dto) {
        TokenDTO token = userService.login(dto.getUserName(), dto.getPassword());
        log.info("{}님 로그인 성공", dto.getUserName());
        return ResponseEntity.ok().body(token);
    }

    /**
     * 아이디 중복검사
     */
    @GetMapping("/checkUserName")
    public ResponseEntity<String> checkUserName(@RequestParam("userName") String userName) {
        if (userService.checkUserName(userName)) {
            // db에 존재하면 CONFLICT 409 ERROR
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이름입니다.");
        }
        return ResponseEntity.ok().body("사용 가능한 이름입니다.");
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/users/me")
    public ResponseEntity<UserInfoRequest> myInfo(Authentication authentication) {
        return ResponseEntity.ok().body(userService.findUser(authentication.getName()).toDTO());
    }

    /**
     * 다른 유저의 정보 조회
     */
    @GetMapping("/users")
    public ResponseEntity<FollowDTO> otherInfo(@RequestParam("userName") String userName, Authentication auth) {
        User selected = userService.findUser(userName);
        User request = userService.findUser(auth.getName());
        return ResponseEntity.ok().body(userService.info(selected, request));
    }

    /**
     * 프로필 수정
     */
    @PatchMapping("/profile")
    public ResponseEntity fixProfile(@RequestBody Profile profile, Authentication auth){
        userService.changeProfile(profile, auth.getName());
        return ResponseEntity.ok().build();
    }


    /**
     * 로그아웃
     */
    // TODO: Logout
    @PostMapping("/users/logout")
    public ResponseEntity logout(Authentication authentication, @RequestHeader(value = "Authorization") String token) {
        userService.logout(token, authentication.getName());
        log.info("{}님 로그아웃", authentication.getName());
        return ResponseEntity.ok().build();
    }


}
