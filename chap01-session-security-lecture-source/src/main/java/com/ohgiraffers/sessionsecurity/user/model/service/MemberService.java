package com.ohgiraffers.sessionsecurity.user.model.service;

import com.ohgiraffers.sessionsecurity.user.model.dao.UserMapper;
import com.ohgiraffers.sessionsecurity.user.model.dto.LoginUserDTO;
import com.ohgiraffers.sessionsecurity.user.model.dto.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Autowired // Service → SecurityConfig 의존성 주입
    private PasswordEncoder encoder; // config의 bean을 끌고 오는 것 (가져다 쓰겠다.)

    @Autowired
    private UserMapper userMapper;

    @Transactional // DML 구문이기 때문에 Transactional 설정 (공부)
                    // DB에 commint하기 전 준비
    public int regist(SignupDTO signupDTO) {

        /* comment. Service 계층은 본격적으로 DB에 값을 넣기 위한
        *   데이터를 가공하는 계층
        *   현재 signupDTO에 userPass 필드는 아직 비밀번호 암호화 되어있지 않음
        *   따라서 DB에 insert 하기 전, 비밀번호를 암호화 할 것. */
        // → SecurityConfig 구성

        // 아직 입력값 그대로 들어있으므로 값 재설정 (암호화)
        signupDTO.setUserPass(encoder.encode(signupDTO.getUserPass()));
        /* 풀이 : signupDTO.getUserPass()으로 값을 가져와서 encode로 감싸주고,
           setUserPass로 암호화 세팅을 한다 */

        int result = userMapper.regist(signupDTO);

        return result;
    }

    public LoginUserDTO findByUsername(String username) {
        /* comment. 사용자의 ID를 전달받아 회원을 조회하는 메서드
        *   username : 사용자의 ID*/
        LoginUserDTO login = userMapper.findByUsername(username);
        if (login == null) {
            return null;
        } else {
            return login;
        }
    }
}
