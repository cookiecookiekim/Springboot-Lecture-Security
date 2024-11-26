package com.ohgiraffers.sessionsecurity.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("login") // 단순히 페이지 리턴을 위한 메서드 추가
    public void loginPage () {} // 실질적으로 post 방식의 login 처리 메서드가 필요하다
                            // → AuthDetails 클래스 추가
    @GetMapping("fail")
    public ModelAndView loginFail (@RequestParam String message, ModelAndView mv){
        // 실패 시 핸들러에서 쿼리스트링으로 보내주는 errorMessage
        mv.addObject("message", message);
        mv.setViewName("auth/fail");
        return mv;
    }
}
