package com.site.bemystory.controller;

import com.site.bemystory.dto.ReplyDTO;
import com.site.bemystory.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;
    @PostMapping("/reply")
    public ResponseEntity saveReply(Authentication auth, @RequestParam("bId") Long bId,
                                    @RequestParam("grpl") Integer grpl, @RequestBody ReplyDTO reply){
        String writer = auth.getName();
        if(grpl==0){
            replyService.writeReply(bId,writer, reply);
        }
        return ResponseEntity.ok().build();
    }
}
