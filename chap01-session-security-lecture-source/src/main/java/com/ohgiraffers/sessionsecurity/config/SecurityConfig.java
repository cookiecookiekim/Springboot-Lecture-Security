package com.ohgiraffers.sessionsecurity.config;

import com.ohgiraffers.sessionsecurity.common.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
/* comment. spring security 기능 활성화를 위한 어노테이션 */
@EnableWebSecurity
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

    // 정적 리소스에 대한 요청은 시큐리티 설정이 돌지 못하게 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer () {
        return web ->
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 로그인 실패했을 때 핸들링 할 메서드 생성
    @Autowired
    private AuthFailHandler authFailHandler;

    /* comment. 이 부분이 설정의 핵심!!!! */
    // 요청에 대한 컨트롤 로직 작성
    // 로그인 필요하면 얘가 위의 URL로 가라고 필터를 걸어놓는 식.
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // 서버의 리소스 접근 가능 권한 설정
        http.authorizeHttpRequests(auth -> {
        // permitAll() → 인증되지 않은(로그인 되지 않은) 사용자들이 접근할 수 있는 URL 기술
        /* 어떤 사이트를 갔는데 처음부터 인증되지 않은 사용자라며 접근이 거부된다? 있을 수 없다.
           →  permitAll로 인증되지 않은 사용자들도 일단 들어갈 수 있게. */
    auth.requestMatchers("/auth/login","/user/signup","/auth/fail","/").permitAll();

    // hasAnyAuthority → 해당하는 URL은 권한을 가진 사람만 접근할 수 있음.
    auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());
    // /user/* 요청은 일반 회원 권한을 가진 사람만 접근할 수 있다.
    auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());
    // 그 외 어떠한 요청들은 권한 상관없이 접근할 수 있음. (단, 로그인 된 인원에 한하여)
    auth.anyRequest().authenticated();
        }).formLogin(login -> {
            login.loginPage("/auth/login"); // 로그인 페이지 url을 기술
            // 사용자가 ID를 입력하는 필드(input 타입 name과 반드시 일치해야 함)
            login.usernameParameter("user"); // 여기에 form 태그의 name(id) 속성 입력
            // 사용자가 PWD를 입력하는 필드 (input 타입 name과 반드시 일치)
            login.passwordParameter("pass"); // 여기에 form 태그의 name(pass) 속성 입력
            // 사용자가 로그인에 성공했을 시 보내줄 URL 기술
            login.defaultSuccessUrl("/", true); // 메인으로 보내줌
            // 로그인에 실패했을 시 내용을 기술한 객체, 호출 아직 미작성
            login.failureHandler(authFailHandler);
        }).logout(logout -> {
            // 로그아웃을 담당할 핸들러 메서드 요청 URL 기술
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            /* session은 쿠키 방식으로 저장되어 있어, 로그인 시
                해당하는 쿠키를 삭제함으로써 로그아웃을 만들어 줌. */
            logout.deleteCookies("JSESSIONID");
            // 서버측의 세선 공간 만료
            logout.invalidateHttpSession(true);
            // 로그아웃 성공 시 요청 URL 기술
            logout.logoutSuccessUrl("/");
        }).sessionManagement(session -> {
            session.maximumSessions(1); // session의 허용 개수 제한
            // 한 사용자가 여러 창을 띄워 동시에 세션 여러개 활성화 방지

            // 세션이 만료 되었을 때 요청할 URL 기술
            session.invalidSessionUrl("/");
            // 추가적인 구현이 필요하므로 비활성화 (나중에 다시 강의)
        }).csrf(csrf -> csrf.disable());

        // 위에서 설정한 내용대로 시큐리티 기능 빌드 (생성)
        return http.build();
    }
}
