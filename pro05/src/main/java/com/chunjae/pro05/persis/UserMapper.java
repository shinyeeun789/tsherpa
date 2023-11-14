package com.chunjae.pro05.persis;

import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.entity.UserRating;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUserList();
    User getUser(String name);
    int getWithdraw(Integer id);
    int getActivate(String name);
    int getDormant(String name);
    User getByEmail(String email);
    User getByName(String name);
    User getUserById(Long id);
    User findById(String email, String tel);
    User findByPw(String email, String tel, String name);
    int userJoin(User euser);
    int updateUser(User euser);
    int updateLevel(String name, String lev);
    int removeUser(User euser);
    UserRating getUserRating(String name);
    List<UserRating> getUserRatingList(String name);
    int updateAccount(User user);

}
