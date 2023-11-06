package com.chunjae.pro05.biz;

import com.chunjae.pro05.domain.UserPrincipal;
import com.chunjae.pro05.entity.Role;
import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.entity.UserRole;
import com.chunjae.pro05.persis.RoleMapper;
import com.chunjae.pro05.persis.UserMapper;
import com.chunjae.pro05.persis.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByLoginId(String loginId) {
        return userMapper.findUserByLoginId(loginId);
    }

    // 회원가입을 위한 사용자 정보를 저장하는 메서드
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        userMapper.setUserInfo(user);
        Role role = roleMapper.getRoleInfo("ADMIN");
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getId());
        userRole.setUserId(role.getId());
        userRoleMapper.setUserRoleInfo(userRole);
    }

    // 사용자 인증을 가지고 사용자 정보 가져오는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByLoginId(username);
        return new UserPrincipal(user);
    }
}
