package com.site.bemystory.controller;

import com.site.bemystory.dto.UserInfoRequest;
import com.site.bemystory.dto.UserJoinRequest;
import com.site.bemystory.dto.UserLoginRequest;
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
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){

        userService.join(dto.getUserName(), dto.getPassword(), dto.getEmail());
        log.info("{}님 회원가입 성공", dto.getUserName());
        return ResponseEntity.ok().body("회원가입 성공했습니다.");
    }

    /**
     * 로그인
     */
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto){
        String token = userService.login(dto.getUserName(), dto.getPassword());
        log.info("{}님 로그인 성공", dto.getUserName());
        return ResponseEntity.ok().body(token);
    }

    /**
     * 아이디 중복검사
     */
    @GetMapping("/checkId")
    public ResponseEntity checkUserName(@RequestParam("userName") String userName){
        if(userService.checkUserName(userName)){
            // db에 존재하면 CONFLICT 409 ERROR
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 내 정보 조회
     */
    @GetMapping("/users/me")
    public ResponseEntity<UserInfoRequest> myInfo(Authentication authentication){
        return ResponseEntity.ok().body(userService.findUser(authentication.getName()).toDTO());
    }

    /**
     * 로그아웃
     */
    //TODO: Logout
}
