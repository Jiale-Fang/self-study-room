package com.example.demo;

import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.vo.ContactVO;
import org.junit.jupiter.api.Test;

public class InterfaceTest {
    private static final UserService userService = new UserServiceImpl();
    private static final UserDao userDao = new UserDao();

    @Test
    public void testGetContactInfo(){
        for (ContactVO contactVO : userDao.getContactInfo(2)) {
            System.out.println(contactVO);
        }
        System.out.println();
        System.out.println(userService.getContactInfo(2));
    }

    @Test
    public void testGetUserCardInfo(){
        System.out.println(userService.getUserCardInfo(2));
        System.out.println(userService.getUserCardInfo(9));
    }
}
