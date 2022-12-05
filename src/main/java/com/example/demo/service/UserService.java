package com.example.demo.service;

import com.example.demo.pojo.User;
import com.example.demo.vo.ContactVO;
import com.example.demo.vo.UserCardVO;

import java.util.List;

public interface UserService {
    String getUserGender(Integer userId);

    UserCardVO getUserCardInfo(Integer userId);

    User getUserInfo(int userId);

    List<ContactVO> getContactInfo(int userId);

    int insert(User user);

    User show(int i);

    int update(User user);

    boolean login(String account, String password);

    boolean exist(String account);

    int getId(String account);
}
