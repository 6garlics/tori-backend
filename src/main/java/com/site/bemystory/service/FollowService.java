package com.site.bemystory.service;

import com.site.bemystory.domain.Follow;
import com.site.bemystory.domain.User;
import com.site.bemystory.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    public String follow(User from_user, User to_user){
        Follow follow = Follow.builder()
                .to_user(to_user)
                .from_user(from_user)
                .build();
        followRepository.save(follow);
        return "Success";
    }
}
