package com.ohgiraffers.sessionsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    /* comment.
    *   비밀번호를 인코딩하기 위한 Bean 생성
    *   Bcrypt 객체는 비밀번호 암호화를 위해 가장 많이
    *   사용되는 알고리즘 중 하나 */

    @Bean // 자바 방식으로 빈 설정
    public PasswordEncoder passwordEncoder () { // PasswordEncoder 인터페이스
        return new BCryptPasswordEncoder();
        // 나중에 BCryptPasswordEncoder 별로라면 다른 거 상속 받으면됨
    }
}
