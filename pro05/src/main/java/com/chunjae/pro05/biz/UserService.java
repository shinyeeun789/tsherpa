package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    public PasswordEncoder passwordEncoder();
    public List<User> getUserList();
    User getUser(String name);
    int getWithdraw(Integer id);
    int getActivate(String name);
    int getDormant(String name);
    User getByEmail(String email);
    public User getByName(String name);
    User getUserById(Long id);
    User findById(String email, String tel);
    User findByPw(String email, String tel, String name);
    int userJoin(User euser);
    int updateUser(User euser);
    int updateLevel(String name, String lev);
    int removeUser(User euser);

}
