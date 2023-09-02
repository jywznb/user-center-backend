package com.vv.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testDigest() {
        String md5 = DigestUtils.md5DigestAsHex("asdfasadf".getBytes());
        System.out.println(md5);
    }


}
