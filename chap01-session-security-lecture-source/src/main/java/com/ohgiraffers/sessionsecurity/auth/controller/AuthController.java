package com.ohgiraffers.sessionsecurity.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("login") // 단순히 페이지 리턴을 위한 메서드 추가
    public void loginPage () { // 실질적으로 post 방식의 login 처리 메서드가 필요하다
                            // → AuthDetails 클래스 추가
    }
}
