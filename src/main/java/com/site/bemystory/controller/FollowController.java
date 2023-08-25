package com.site.bemystory.controller;

import com.site.bemystory.domain.User;
import com.site.bemystory.dto.FollowDTO;
import com.site.bemystory.service.FollowService;
import com.site.bemystory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final UserService userService;
    private final FollowService followService;

    /**
     * 친구 추가
     */
    @PostMapping("/users/follow/{friendName}")
    public ResponseEntity follow(Authentication authentication, @PathVariable("friendName") String friendName) {
        User from_user = userService.findUser(authentication.getName());
        User to_user = userService.findUser(friendName);
        followService.follow(from_user, to_user);
        return ResponseEntity.ok().build();
    }

    /**
     * 팔로잉 조회
     */
    @GetMapping("/users/following")
    public ResponseEntity<List<FollowDTO>> getFollowingList(Authentication authentication) {
        User from_user = userService.findUser(authentication.getName());
        return ResponseEntity.ok().body(followService.followingList(from_user));
    }

    /**
     * 팔로워 조회
     */
    @GetMapping("/users/follower")
    public ResponseEntity<List<FollowDTO>> getFollowerList(Authentication authentication) {
        User to_user = userService.findUser(authentication.getName());
        return ResponseEntity.ok().body(followService.followerList(to_user));
    }
}
