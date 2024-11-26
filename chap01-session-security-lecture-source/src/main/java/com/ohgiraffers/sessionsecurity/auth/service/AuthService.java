package com.ohgiraffers.sessionsecurity.auth.service;

import com.ohgiraffers.sessionsecurity.auth.model.AuthDetails;
import com.ohgiraffers.sessionsecurity.user.model.dto.LoginUserDTO;
import com.ohgiraffers.sessionsecurity.user.model.service.MemberService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/* comment. security에서 사용자의 아이디를 인증하기 위한 인터페이스.
*   loadUserByUsername을 필수로 구현해야 하며, 로그인 인증 시
*   해당 메서드에 login 시 전달된 사용자의 id를 매개변수로 전달받게 된다. */
@Service
public class AuthService implements UserDetailsService {

    private final MemberService memberService;

    // 매개변수가 하나이므로 Autowired 없이도 주입 가능.
    public AuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* comment. loadUserByUsername 메서드
        *   사용자의 이름을 가져오는 것 같지만 실제로 로그인 시,
        *   사용자를 식별하는 ID를 매개변수로 받게 된다.
        *   따라서 매개변수를 SQL문에 전달하여 해당하는
        *   회원을 DB에서 조회하는 로직을 내부에서 구현하면 된다. */
        LoginUserDTO login = memberService.findByUsername(username);

        if (login == null) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        /* comment. return 타입이 UserDetails 타입이기 때문에 실제로 구현한
        *   AuthDetails 클래스에 DB에서 조회해온 사용자에 대한 정보를 담아 return */

        return new AuthDetails(login);
    }
}
