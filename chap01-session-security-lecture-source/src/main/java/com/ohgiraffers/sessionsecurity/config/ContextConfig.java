package com.ohgiraffers.sessionsecurity.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.ohgiraffers.sessionsecurity") // bean 스캔할 수 있는 스캔 기능 설정
@MapperScan(basePackages = "com.ohgiraffers.sessionsecurity", annotationClass = MapperScan.class)
// 마이바티스 Mapper를 스캔할 수 있는 MapperScan 추가
public class ContextConfig {
}
