package com.site.bemystory.controller;

import com.site.bemystory.dto.UserJoinRequest;
import com.site.bemystory.dto.UserLoginRequest;
import com.site.bemystory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto){

        userService.join(dto.getUserName(), dto.getPassword(), dto.getEmail());
        log.info("{}님 회원가입 성공", dto.getUserName());
        return ResponseEntity.ok().body("회원가입 성공했습니다.");
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto){
        String token = userService.login(dto.getUserName(), dto.getPassword());
        log.info("{}님 로그인 성공", dto.getUserName());
        return ResponseEntity.ok().body(token);
    }
}
