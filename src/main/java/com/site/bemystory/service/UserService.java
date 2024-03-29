package com.site.bemystory.service;

import com.site.bemystory.domain.User;
import com.site.bemystory.dto.FollowDTO;
import com.site.bemystory.dto.Profile;
import com.site.bemystory.dto.TokenDTO;
import com.site.bemystory.exception.AppException;
import com.site.bemystory.exception.ErrorCode;
import com.site.bemystory.exception.LogoutException;
import com.site.bemystory.exception.UserException;
import com.site.bemystory.repository.UserRepository;
import com.site.bemystory.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FollowService followService;
    private final RedisTemplate redisTemplate;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.token.secret}")
    private String key;
    private Long expireTimeMs = 1000 * 60 * 600L;

    public boolean checkUserName(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }

    public String join(String userName, String password, String email) {
        /*
         //username 중복 CHECK
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "는 이미 있습니다");
                });
         */
        if(checkUserName(userName)){
            throw new AppException(ErrorCode.USERNAME_DUPLICATED, "이미 존재하는 이름입니다.");
        }

        // 저장
        User user = User.builder()
                .userName(userName)
                .password(encoder.encode(password))
                .email(email)
                .build();
        userRepository.save(user);
        return "SUCCESS";
    }

    public TokenDTO login(String userName, String password) {
        //userName 없음
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "이 없습니다."));
        //password 틀림
        if (!encoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력했습니다.");
        }

        TokenDTO token = TokenDTO.builder()
                .token(JwtTokenUtil.createToken(selectedUser.getUserName(), key, expireTimeMs))
                .build();
        //redis에 토큰 저장
        redisTemplate.opsForValue().set("AT:" + userName, token.getToken(), expireTimeMs, TimeUnit.MILLISECONDS);

        //last login time update
        selectedUser.setLastLogin(new Timestamp(new Date().getTime()));
        //error 없으면 token 발행
        return token;
    }

    public void logout(String token, String userName){
        token = token.split(" ")[1];
        if(JwtTokenUtil.isExpired(token, key)){
            throw new LogoutException(ErrorCode.INVALID_TOKEN, "로그아웃: 유효하지 않은 토큰입니다.");
        }
        // Redis에서 username으로 저장된 token이 있는지 여부 확인 후 있을 시 삭제
        if(redisTemplate.opsForValue().get("AT:"+userName)!=null){
            redisTemplate.delete("AT:"+userName);
        }
        // 해당 Access Token 유효시간을 가지고 와서 BlackList에 저장하기
        Long expiration =  JwtTokenUtil.getExpiration(token, key);
        redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    public User findUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND, "\"" + userName + "\"" + "은 존재하지 않는 사용자입니다."));
    }

    //다른 유저 프로필 조회
    public FollowDTO info(User selected, User request){
        return selected.toFollow(followService.findStatus(selected, request));
    }

    //프로필 변경
    public void changeProfile(Profile profile, String userName){
        User selected = findUser(userName);
        if(!StringUtils.hasText(profile.getProfile())) selected.setUserName(profile.getUserName());
        else{
            selected.setProfile(profile.getProfile());
            if(StringUtils.hasText(profile.getUserName())) selected.setUserName(profile.getUserName());
        }
    }
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }
}
