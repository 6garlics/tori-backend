package com.site.bemystory.service;

import com.site.bemystory.domain.User;
import com.site.bemystory.exception.AppException;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String join(String userName, String password, String email) {
        //username 중복 CHECK
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "는 이미 있습니다");
                });

        // 저장
        User user = User.builder()
                .userName(userName)
                .password(password)
                .email(email)
                .build();
        userRepository.save(user);
        return "SUCCESS";
    }
}
