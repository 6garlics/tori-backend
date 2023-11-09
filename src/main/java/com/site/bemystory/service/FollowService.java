package com.site.bemystory.service;

import com.site.bemystory.domain.Follow;
import com.site.bemystory.domain.User;
import com.site.bemystory.dto.FollowDTO;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.exception.FollowException;
import com.site.bemystory.repository.FollowRepository;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    //A와 B의 follow관계 찾기
    protected String findStatus(User selectedUser, User requestUser) {
        if (selectedUser.getUserName() == requestUser.getUserName())
            return "self";
        if (followRepository.findFollow(requestUser, selectedUser).isEmpty())
            return "none";

        return "following";
    }

    //follow 요청
    public String follow(User from_user, User to_user) {
        // 자기 자신 follow 안됨
        if (from_user == to_user)
            throw new FollowException(ErrorCode.INVALID_REQUEST, "자기 자신을 follow할 수 없습니다.");
        // 중복 follow x
        if (followRepository.findFollow(from_user, to_user).isPresent())
            throw new FollowException(ErrorCode.FOLLOW_DUPLICATED, "이미 follow했습니다.");
        Follow follow = Follow.builder()
                .toUser(to_user)
                .fromUser(from_user)
                .build();
        followRepository.save(follow);
        return "Success";
    }

    //following 리스트
    public List<FollowDTO> followingList(User selectedUser, User requestUser) {
        List<Follow> list = followRepository.findByFromUser(selectedUser);
        List<FollowDTO> followList = new ArrayList<>();
        for (Follow f : list) {
            followList.add(userRepository.findByUserName(f.getToUser().getUserName())
                    .orElseThrow().toFollow(findStatus(f.getToUser(), requestUser)));
        }
        return followList;
    }

    //follower list
    public List<FollowDTO> followerList(User selectedUser, User requestUser) {
        List<Follow> list = followRepository.findByToUser(selectedUser);
        List<FollowDTO> followerList = new ArrayList<>();
        for (Follow f : list) {
            followerList.add(userRepository.findByUserName(f.getFromUser().getUserName())
                    .orElseThrow().toFollow(findStatus(f.getFromUser(), requestUser)));
        }
        return followerList;
    }

    //TODO: follow 취소
    public String cancelFollow(String from_user, String to_user) {
        User from = userRepository.findByUserName(from_user).orElseThrow();
        User to = userRepository.findByUserName(to_user).orElseThrow();
        followRepository.deleteFollowByFromUserAndToUser(from, to);
        return "Success";
    }
}
