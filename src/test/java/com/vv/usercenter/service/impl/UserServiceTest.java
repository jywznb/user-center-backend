package com.vv.usercenter.service.impl;

import com.vv.usercenter.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 *
 * @author jyw
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("jyw");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("123");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "jywznb";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String userId = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, userId);

//        Assertions.assertEquals(-1, result);
//        Assertions.assertTrue();
    }
}