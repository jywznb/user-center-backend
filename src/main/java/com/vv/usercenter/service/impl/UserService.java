package com.vv.usercenter.service.impl;

import com.vv.usercenter.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author jywzn
* @description 针对表【user(用户信息)】的数据库操作Service
* @createDate 2023-08-23 02:13:46
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param userId
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String userId);

    /**
     * 用户登录
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param request
     * @return 用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);

    /**
     * 请求用户对象
     * @param request
     * @return
     */
    Boolean userLogout(HttpServletRequest request);
}
