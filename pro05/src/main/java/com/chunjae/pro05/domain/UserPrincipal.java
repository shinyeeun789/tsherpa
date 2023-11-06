package com.chunjae.pro05.domain;

import com.chunjae.pro05.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

// UserDetails(스프링 시큐리티에서 기본적으로 제공하는 인터페이스)
@Data
public class UserPrincipal implements UserDetails {
    
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new UserGrant());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {          // 계정이 없다는 의미
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {           // 인가된 사용자만 들어올 수 있도록
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {                    // active가 1이면 true, 0이면 false
        return user.getActive() == 1;
    }

    public String getId() {
        return user.getLoginId();
    }

    public String getName() {
        return user.getUserName();
    }
}
