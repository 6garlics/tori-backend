package com.site.bemystory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncoderConfig {
    /**
     * SecurityConfig와 BcryptPasswordEncoder는 다른 클래스에 선언
     * 순환참조 문제가 발생할 수 있기 때문
     */
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
