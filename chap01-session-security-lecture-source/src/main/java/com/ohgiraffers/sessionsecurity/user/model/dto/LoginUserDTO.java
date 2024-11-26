package com.ohgiraffers.sessionsecurity.user.model.dto;

import com.ohgiraffers.sessionsecurity.common.UserRole;
import lombok.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginUserDTO { // login.html 묶어줄 DTO 구성
    // login.html의 form 태그 name 속성 값인 user, pass만
    // 작성하는줄 알았으나 모든 정보 기억

    /* comment.
    *   로그인 시 사용할 DTO 클래스는 화면상에서 입력받는
    *   ID , PASSWORD 뿐 아니라 로그인에 성공 했을 시
    *   필요한 정보들을 추가적으로 담는다. */

    private int userCode;
    private String userId;
    private String userName;
    private String password;
    private UserRole userRole; // 관리자 , 일반회원 두가지 이므로 상수로써 만들것

    // UserRole, Enum으로 클래스 생성

    // 다중 권한일 때만 사용하는 메서드이므로, 어렵다고 생각하지 말기.
    public List<String> getRole() { // 반환 타입 List<String>으로 변경
        if (this.userRole.getRole().length() > 0) {
            /* 회원의 권한이 여러개 ex) 일반회원 + 관리자 일 시,
               두 권한을 담기 위한 리스트 */
            return Arrays.asList(this.userRole.getRole().split(","));
        }
        return new ArrayList<>();
    }
}
