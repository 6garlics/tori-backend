package com.site.bemystory.service;

import com.site.bemystory.domain.Follow;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.FollowDTO;
import com.site.bemystory.repository.FollowRepository;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    //follow 요청
    public String follow(User from_user, User to_user){
        Follow follow = Follow.builder()
                .toUser(to_user)
                .fromUser(from_user)
                .build();
        followRepository.save(follow);
        return "Success";
    }

    //following 리스트
    public List<FollowDTO> followingList(User user){
        List<Follow> list=followRepository.findByFromUser(user).orElseThrow();
        List<FollowDTO> followList = new ArrayList<>();
        for(Follow f : list){
            followList.add(userRepository.findByUserName(f.getToUser().getUserName())
                    .orElseThrow().toFollow("following"));
        }
        return followList;
    }

    //follower list
    public List<FollowDTO> followerList(User user){
        List<Follow> list=followRepository.findByToUser(user).orElseThrow();
        List<FollowDTO> followerList = new ArrayList<>();
        for(Follow f : list){
            followerList.add(userRepository.findByUserName(f.getFromUser().getUserName())
                    .orElseThrow().toFollow("following"));
        }
        return followerList;
    }
}
