package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.entity.UserRating;
import com.chunjae.pro05.persis.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public User getUser(String name) {
        return userMapper.getUser(name);
    }

    @Override
    public int getWithdraw(Integer id) {
        return userMapper.getWithdraw(id);
    }

    @Override
    public int getActivate(String name) {
        return userMapper.getActivate(name);
    }

    @Override
    public int getDormant(String name) {
        return userMapper.getDormant(name);
    }

    @Override
    public User getByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    @Override
    public User getByName(String name) {
        return userMapper.getByName(name);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User findById(String email, String tel) {
        return userMapper.findById(email, tel);
    }

    @Override
    public User findByPw(String email, String tel, String name) {
        return userMapper.findByPw(email, tel, name);
    }

    @Override
    public int userJoin(User User) {
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        return userMapper.userJoin(User);
    }

    @Override
    public int updateUser(User User) {
        return userMapper.updateUser(User);
    }

    @Override
    public int updateLevel(String name, String lev) {
        return userMapper.updateLevel(name, lev);
    }

    @Override
    public int removeUser(User User) {
        return userMapper.removeUser(User);
    }

    @Override
    public UserRating getUserRating(String name) {
        return userMapper.getUserRating(name);
    }
}
