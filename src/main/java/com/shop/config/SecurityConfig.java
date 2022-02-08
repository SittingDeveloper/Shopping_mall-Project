package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    MemberService memberService;

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
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.formLogin()
                .loginPage("/members/login") // 로그인 페이지 URL
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL
                .usernameParameter("email") // 로그인 시 사용할 파라미터 이름으로 email 을 지정
                .failureUrl("/members/login/error") // 로그인에 실패시 이동할 URL
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL 지정
                .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 URL

        http.authorizeRequests() // Security 처리에 HttpServletRequest 를 사용
                .mvcMatchers("/", "/members/**",
                        "/item/**", "/images/**").permitAll() // 모든 사용자가 인증없이 해당 경로 접근 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN") // /admin 으로 시작하는 경로는 ADMIN Role 일 때만 접근 가능
                .anyRequest().authenticated(); // 위 두 경로를 제외한 경로들은 모두 인증을 필요로 하도록 설정

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()); // 인증되지 않은 사용자가 리소스에 접근할 때 수행되는 핸들러
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 디렉터리의 하위 파일은 인증을 무시하도록 설정
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        스프링 시큐리티에서 인증은 AuthenticationManager 를 통해 이루어지며 AuthenticationManagerBuilder 가 AuthenticationManager 를 생성한다.
        userDetailService 를 구현하고 있는 객체로 memberService 를 지정 해주며, 비밀번호 암호화를 위해 passwordEncoder 를 지정해준다.
         */
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }
}