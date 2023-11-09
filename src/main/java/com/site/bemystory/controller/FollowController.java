package com.site.bemystory.controller;

import com.site.bemystory.domain.User;
import com.site.bemystory.dto.FollowDTO;
import com.site.bemystory.service.FollowService;
import com.site.bemystory.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/users/{userName}/following")
    public ResponseEntity<List<FollowDTO>> getFollowingList(@PathVariable("userName") String userName, Authentication auth) {
        User from_user = userService.findUser(userName);
        User requestUser=userService.findUser(auth.getName());
        return ResponseEntity.ok().body(followService.followingList(from_user, requestUser));
    }

    /**
     * 팔로워 조회
     */
    @GetMapping("/users/{userName}/follower")
    public ResponseEntity<List<FollowDTO>> getFollowerList(@PathVariable("userName") String userName, Authentication auth) {
        User to_user = userService.findUser(userName);
        User requestUser=userService.findUser(auth.getName());
        return ResponseEntity.ok().body(followService.followerList(to_user, requestUser));
    }

    /**
     * 팔로우 취소
     */
    @DeleteMapping("/users/follow/{friendName}")
    public ResponseEntity deleteFollow(Authentication authentication, @PathVariable("friendName") String friendName){
        return ResponseEntity.ok(followService.cancelFollow(authentication.getName(), friendName));
    }
}
