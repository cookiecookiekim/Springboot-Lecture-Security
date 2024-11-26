package com.ohgiraffers.sessionsecurity.auth.model;


import com.ohgiraffers.sessionsecurity.user.model.dto.LoginUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* comment.
*   UserDetailService에서 사용자의 이름을 기준으로 조회한 결과가
*   반환되는 사용자 타입으로 해당 객체에 조회된 사용자의 정보가 담겨,
*   Session에 저장되게 한다. */
@NoArgsConstructor
@AllArgsConstructor
@Getter // 아래의 Getter와 다름
@Setter
public class AuthDetails implements UserDetails { // 노션에 필기한 거 대조해가면서 공부

    // 로그인 시 사용하는 사용자 정보 DTO를 필드로 갖는다.
    private LoginUserDTO loginUserDTO;

    @Override
    public boolean isAccountNonExpired() {
        /* comment. 계정의 만료 여부를 표현하는 메서드.
        *   return 값이 false이면 해당하는 계정을 사용할 수 없게 된다. */
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        /* comment. 잠겨있는 계정을 확인하는 메서드.
        *   false면 해당 계정이 잠겨, 사용이 불가능하다.
        *   ex) 비밀번호 반복 실패 시 일시적인 lock or 오랜기간 비접속 휴면 처리 */
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        /* comment. 탈퇴 계정 여부를 표현하는 메서드
        *   false면 해당 계정 사용 불가 */
        return true;
    }

    @Override
    public boolean isEnabled() {
        /* comment. 계정 비활성화 여부로 사용자가 사용할 수 없는 상태
        *   false면 계정 사용 불가
        *   ex) 삭제 처리 같은 경우*/
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // ☆ 중요 ☆
        /* comment. 권한 정보를 반환하는 메서드. (다중 권한 : 일반 사용자 , 관리자)
         *       다중 권한을 위해 Collection 타입으로 return 타입 지정.
         *       사용자의 권한 정보를 넣을 때 사용되는 메서드. */
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        loginUserDTO.getRole(); // getRole 생성

        return List.of();
    }

    @Override
    public String getPassword() { // ☆ 중요 ☆
        /* comment. 사용자의 비밀번호를 반환하는 메서드.
        *   UsernamePasswordAuthenticationToken과
        *   사용자의 비밀번호를 비교할 때 사용. */
        return "";
    }

    @Override
    public String getUsername() { // ☆ 중요 ☆
        /* comment. 사용자의 아이디를 반환하는 메서드.
        *   UsernamePasswordAuthenticationToken과
        *   사용자의 아이디를 비교할 때 사용. */
        return "";
    }
}
