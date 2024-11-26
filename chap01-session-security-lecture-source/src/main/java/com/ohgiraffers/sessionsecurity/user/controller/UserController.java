package com.ohgiraffers.sessionsecurity.user.controller;

import com.ohgiraffers.sessionsecurity.user.model.dao.UserMapper;
import com.ohgiraffers.sessionsecurity.user.model.dto.SignupDTO;
import com.ohgiraffers.sessionsecurity.user.model.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user") // user에 대한 요청을 모두 낚아 채기 (뒤에 * 안해도 됨)
public class UserController {

    // 수업은 시간상 필드 주입만.
    private MemberService memberService;

    @Autowired // 생성자 주입 : 컨트롤러 → 서비스 의존성 주입
    public UserController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("signup")
    public void signupPage() {}

    @PostMapping("signup")
    public ModelAndView signupPage(@ModelAttribute SignupDTO signupDTO, ModelAndView mv) {
        // void : 동일한 이름으로 구성 (user 패키지에 signup이라는 html 파일을 만들어줌)

        Integer result = memberService.regist(signupDTO);
        // 원시 타입은 null이 될 수 없기 때문에 Integer로 변경

        String message = null;

        /* comment. controller의 역할은 어떠한 view를 보여줄지 선택. */
        if (result == null) {
            message = "중복된 회원이 존재합니다";
        } else if (result == 0) { // 0이 찍히면 개발자 코드 잘못
            message = "서버 내부에서 오류가 발생했습니다.";
            mv.setViewName("user/signup");
        } else if (result >= 1) {
            message = "회원 가입이 완료되었습니다.";
            mv.setViewName("auth/login");
        }

        mv.addObject("message", message);

        return mv;
    }
}
