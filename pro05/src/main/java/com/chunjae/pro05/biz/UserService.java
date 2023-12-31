package com.chunjae.pro05.biz;

import com.chunjae.pro05.entity.User;
import com.chunjae.pro05.entity.UserRating;
import com.chunjae.pro05.entity.UserRatingVO;
import com.chunjae.pro05.util.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    public PasswordEncoder passwordEncoder();
    public List<User> getUserList();
    public User getUser(String name);
    public int getWithdraw(Integer id);
    public int getActivate(String name);
    public int getDormant(String name);
    public User getByEmail(String email);
    public User getByName(String name);
    public User getUserById(Long id);
    public User findById(String email, String tel);
    public User findByPw(String email, String tel, String name);
    public int userJoin(User euser);
    public int updateUser(User euser);
    public int updateLevel(String name, String lev);
    public int removeUser(User euser);
    public UserRating getUserRating(String name);
    public List<UserRating> getUserRatingList(String name);
    public int updateAccount(User user);
    public int insertUserRating(UserRating userRating);
    public int editUserRating(UserRating userRating);
    public List<UserRatingVO> userMgmtList(Page page);
    public int getUserMgmtCount(Page page);
    public UserRatingVO getUserRatingVO(String name);
    public List<UserRatingVO> userRatingListInUserDetail(Page page);
    public int updateUserAct(User user);

}
