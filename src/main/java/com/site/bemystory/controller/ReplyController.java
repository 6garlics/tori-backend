package com.site.bemystory.controller;

import com.site.bemystory.dto.ReplyDTO;
import com.site.bemystory.dto.ReplyListRequest;
import com.site.bemystory.dto.ReplyRequest;
import com.site.bemystory.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReplyController {
    private final ReplyService replyService;

    /**
     * 모댓글 등록
     */
    @PostMapping("/reply")
    public ResponseEntity<ReplyRequest> saveReply(Authentication auth, @RequestParam("bId") Long bId,
                                                  @RequestParam("grpl") Integer grpl, @RequestBody ReplyDTO reply){
        String writer = auth.getName();
        return ResponseEntity.ok(replyService.writeReply(bId,writer, reply));
    }

    /**
     * 댓글 리스트
     */
    @GetMapping("/reply/{bookId}")
    public ResponseEntity<ReplyListRequest> listReply(@PathVariable Long bookId){
        return ResponseEntity.ok(replyService.listReply(bookId));
    }

    /**
     * 모댓글 삭제
     */
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<ReplyRequest> deleteReply(Authentication auth, @PathVariable Long replyId){
        return ResponseEntity.ok(replyService.deleteReply(auth.getName(),replyId));
    }
}
