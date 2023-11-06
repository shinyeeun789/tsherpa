package com.chunjae.pro05.domain;

import org.springframework.security.core.GrantedAuthority;

// GrantedAuthority (관리자 권한 인터페이스)
public class UserGrant implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ADMIN";
    }
}
