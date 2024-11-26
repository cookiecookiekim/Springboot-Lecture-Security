package com.ohgiraffers.sessionsecurity.common;

// LoginUserDTO 에서 넘어옴.

import lombok.Getter;

@Getter // 값 꺼내오기 위한 Getter만 설정
public enum UserRole {

    /* comment. enum이란?
    *   열거형 상수들의 집합을 의미.
    *   주로 사용되는 예시로는 고정되어있는 값들을
    *   처리하기 위해 사용됨
    *   ex) 시스템의 권한이 단 2개 - 일반 사용자 , 관리자 */

    // 상수 필드 설정
    USER("USER"), ADMIN("ADMIN");

    private String role;

    UserRole(String role) {this.role = role;}
}
