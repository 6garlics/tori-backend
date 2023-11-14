package com.site.bemystory.controller;

import com.site.bemystory.dto.LetterRequest;
import com.site.bemystory.service.LetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LetterController {
    private final LetterService letterService;
    /**
     * 편지 보내기
     */
    @PostMapping("/letter")
    public ResponseEntity<String> sendLetter(Authentication auth, @RequestParam("to") String toUser, @RequestBody LetterRequest letter){
        String fromUser = auth.getName();   //편지 보내는 사람
        return ResponseEntity.ok(letterService.send(fromUser,toUser, letter));
    }

    /**
     * 편지 리스트 - 동화별
     */

    /**
     * 편지 리스트 - 유저별
     */
}
