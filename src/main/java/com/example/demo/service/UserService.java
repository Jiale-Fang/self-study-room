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

}
