package com.wndudzz6.codereviewer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")  // 리액트 개발 주소
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true); // 필요 시 쿠키 허용
    }
}
