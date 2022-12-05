package com.example.demo;

import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;

public class InterfaceTest {
    private static final UserService userService = new UserServiceImpl();

    @Test
    public void testGetContactInfo(){
        System.out.println(userService.getContactInfo(2));
    }

    @Test
    public void testGetUserCardInfo(){
        System.out.println(userService.getUserCardInfo(2));
        System.out.println(userService.getUserCardInfo(9));
    }
}
