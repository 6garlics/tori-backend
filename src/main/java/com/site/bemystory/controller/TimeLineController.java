package com.site.bemystory.controller;

import com.site.bemystory.domain.Book;
import com.site.bemystory.dto.BookDTO;
import com.site.bemystory.dto.SliceResponse;
import com.site.bemystory.service.TimeLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TimeLineController {
    private final TimeLineService timeLineService;

    @GetMapping("/timeline")
    public ResponseEntity<SliceResponse> timeline(Authentication auth, Pageable pageable){
        return ResponseEntity.ok().body(timeLineService.processBookExceptNowUser(auth.getName(), pageable));
    }
}
