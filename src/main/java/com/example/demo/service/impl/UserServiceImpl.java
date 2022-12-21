package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.util.ChatLogTimeUtil;
import com.example.demo.vo.ContactVO;
import com.example.demo.vo.UserCardVO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserServiceImpl implements UserService {

    private final static UserDao userDao = new UserDao();

    @Override
    public String getUserGender(Integer userId){
        return userDao.getUserGender(userId);
    }

    @Override
    public UserCardVO getUserCardInfo(Integer userId){
        return userDao.getUserCardInfo(userId);
    }

    @Override
    public User getUserInfo(int userId) {
        return userDao.getUserInfo(userId);
    }

    @Override
    public List<ContactVO> getContactInfo(int userId) {
        List<ContactVO> temp = userDao.getContactInfo(userId);
        List<ContactVO> contactVOList = new ArrayList<>();
        Set<Integer> friendIdSet = new HashSet<>();
        //Get the latest message between you and your all friends
        for (ContactVO contactVO : temp) {
            if (!friendIdSet.contains(contactVO.getFriendId())){
                friendIdSet.add(contactVO.getFriendId());
                contactVO.setFormatTime(ChatLogTimeUtil.formatTime(contactVO.getCreateTime()));
                contactVOList.add(contactVO);
            }
        }
        return contactVOList;
    }

}
