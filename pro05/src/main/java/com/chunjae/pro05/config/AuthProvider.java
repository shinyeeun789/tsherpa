package com.chunjae.pro05.config;

import com.chunjae.pro05.biz.UserService;
import com.chunjae.pro05.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
//인증 관리자
@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = (String) authentication.getPrincipal(); // 로그인 창에 입력한 name
        String password = (String) authentication.getCredentials(); // 로그인 창에 입력한 password

        PasswordEncoder passwordEncoder = userService.passwordEncoder();
        UsernamePasswordAuthenticationToken token;
        User userVo = userService.getByName(name);

        if (userVo != null && passwordEncoder.matches(password, userVo.getPassword())) { // 일치하는 user 정보가 있는지 확인
            List<GrantedAuthority> roles = new ArrayList<>();
            if(userVo.getLev().equals("ADMIN")){
                roles.add(new SimpleGrantedAuthority("ADMIN")); // 권한 부여
            } else if(userVo.getLev().equals("ADMIN")){
                roles.add(new SimpleGrantedAuthority("EMP")); // 권한 부여
            } else {
                roles.add(new SimpleGrantedAuthority("USER")); // 권한 부여
            }

            if (userVo.getAct().equals("JOIN")) {
                token = new UsernamePasswordAuthenticationToken(userVo.getName(), null, roles);
                // 인증된 user 정보를 담아 SecurityContextHolder에 저장되는 token
                return token;
            } else {
                throw new DisabledException("신고 누적으로 정지된 계정입니다.");
            }
        }

        throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않거나 가입되지 않은 아이디입니다.");
        // Exception을 던지지 않고 다른 값을 반환하면 authenticate() 메서드는 정상적으로 실행된 것이므로 인증되지 않았다면 Exception을 throw 해야 한다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 인증 시스템을 사용하겠다는 의미
        return true;
    }
}