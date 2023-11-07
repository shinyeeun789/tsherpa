package com.chunjae.pro05.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS 관리자
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;             // 데이터를 주고받기 충분한 시간인 1분으로 설정

    // 특정 포트 열어두기
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8085")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTION")      // 해당 메서드 허용
                .allowedHeaders("*")                                                    // 모든 헤더 허용
                .allowCredentials(true)                                                 // 보안 허용 처리
                .maxAge(MAX_AGE_SECS);                                                  // 허용 시간
    }
}
