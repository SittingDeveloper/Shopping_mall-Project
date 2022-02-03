package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    /*
    WebSecurityConfigurerAdapter 를 상속받는 클래스에 @EnableWebSecurity Annotation 을 선언하면
    SpringSecurityFilterChain 이 자동으로 포함된다. WebSecurityConfigurerAdapter 를 상속받아서
    메소드 오버라이딩을 통해 보안 설정을 커스터마이징 할 수 있다.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 요청에 대한 보안을 설정. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정 작성
        /*
        Security Dependency 를 추가하면
        모든 요청에 인증을 필요로 하지만 SecurityConfig 의 configure 메소드에
        설정을 추가하지 않으면 요청에 인증을 요구하지 않는다.
         */

    }

    /*
    비밀번호를 DB 에 그대로 저장했을 경우
    DB가 해킹당하면 고객의 회원정보가 그대로 노출됨.
    이를 해결하기 위해 BCryptoPasswordEncoder 의 해시함수를 이용하여 비밀번호를 암호화하여 저장.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
