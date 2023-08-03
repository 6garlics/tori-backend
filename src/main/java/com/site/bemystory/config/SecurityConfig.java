package com.site.bemystory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        /*return httpSecurity.httpBasic().disable()
                .csrf().disable()
                .cors().and()
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/users/join", "/users/login").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();*/
        //spring 공식문서 읽고 만듦 위에 것은 deprecated 되어서
        return httpSecurity.authorizeHttpRequests((requests)->requests
                .requestMatchers("/**").permitAll().requestMatchers("/users/join", "/users/login").permitAll()
                .anyRequest().authenticated())
                .httpBasic(httpBasic-> httpBasic.disable())
                .csrf(csrf-> csrf.disable())
                .formLogin((form)->form
                        .loginPage("/users/login")
                        .permitAll())
                .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout((logout)->logout.permitAll())
                .build();


    }
}
