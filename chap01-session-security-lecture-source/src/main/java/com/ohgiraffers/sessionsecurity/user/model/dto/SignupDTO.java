package com.ohgiraffers.sessionsecurity.user.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SignupDTO {
    // signup.html의 form 태그의 name과 동일하게 필드값 작성
    // → 동일하게 mapping 시켜줘야 하기 때문
    // → db와 맞춰도 되고 화면상에 맞춰도 되고 유동적으로 사용하면 됨
    private String userId;
    private String userName;
    private String userPass;
    private String role;

}
