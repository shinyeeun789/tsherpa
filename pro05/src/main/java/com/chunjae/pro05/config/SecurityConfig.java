package com.chunjae.pro05.config;

import com.chunjae.pro05.biz.*;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

// 스프링 시큐리티 설정 관리자
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserService userService() { return new UserServiceImpl();  }

    @Bean
    public FreeService freeService() { return new FreeServiceImpl(); }

    @Bean
    public FreeCommentService freeCommentService() { return new FreeCommentServiceImpl(); }

    @Bean
    public TradeCategoryService tradeCategoryService() { return new TradeCategoryImpl(); }

    @Bean
    public PaymentService paymentService() { return new PaymentServiceImpl(); }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 권한에 따라 허용하는 url 설정
        // .antMatchers는 /login, /join 페이지는 모두 허용, 다른 페이지는 인증된 사용자만 허용
        // 자원의 경로는 mvcMatchers 로
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login.do", "/term.do", "/idCheck.do", "/join.do").permitAll()
                .mvcMatchers("/","/resources/**", "/bootstrap/**", "/css/**", "/ckeditor/**", "/js/**", "/images/**", "/upload/**", "/jquery/**", "/webfonts/**").permitAll()
                .anyRequest().authenticated();
        // login 설정
        http
                .formLogin()
                .loginPage("/login.do")            // GET 요청 (login form을 보여줌)
                .loginProcessingUrl("/auth.do")    // POST 요청 (login 창에 입력한 데이터를 처리)
                .usernameParameter("name")      // login에 필요한 id 값을 email로 설정 (default는 username)
                .passwordParameter("password")  // login에 필요한 password 값을 password(default)로 설정
                .defaultSuccessUrl("/");        // login에 성공하면 /로 redirect
        // logout 설정
        http
                .logout()
                .logoutUrl("/logout.do")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/");         // logout에 성공하면 /로 redirect

        // cors 방지 해제
        http.csrf().disable().cors().disable();

        // 중복 로그인 방지
        http.sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(1)
                .expiredSessionStrategy(new CustomSessionExpiredStrategy())
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry());

        return http.build();
    }

    // CORS 문제 해결을 위한 코드
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

}