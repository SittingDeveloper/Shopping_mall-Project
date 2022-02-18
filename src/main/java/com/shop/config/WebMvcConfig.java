package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    // application.properties 에 설정한 uploadPath 프로퍼티 값을 읽어옴
    @Value("${uploadPath}")
    String uploadPath;

    /*
    웹 브라우저에 입력하는 url 에 /images 로 시작하는 경우
    uploadPath 에 설정한 폴더를 기준으로 파일을 읽어오도록 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").
                addResourceLocations(uploadPath); // 로컬 컴퓨터에 저장된 파일을 읽어올 root 경로를 설정
    }
}
