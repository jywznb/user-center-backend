package com.vv.usercenter.service.impl.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vv.usercenter.common.ErrorCode;
import com.vv.usercenter.constant.UserConstant;
import com.vv.usercenter.exception.BussinessException;
import com.vv.usercenter.mapper.UserRoleTypeEnum;
import com.vv.usercenter.model.entity.User;
import com.vv.usercenter.service.impl.UserService;
import com.vv.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author jywzn
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2023-08-23 02:13:46
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 盐值，混淆密码
     */
    private static final String SAULT = "jyw";

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String userId) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, userId)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        if (userAccount.length() < 4) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

        if (userId.length() > 6) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR, "用户Id过短");
        }

        //账户不能包含特殊字符
        String regex = ".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        //密码和校验密码不能相同
        if (!userPassword.equals(checkPassword)) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        int count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        //账户id不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BussinessException(ErrorCode.PARAMS_ERROR);
        }

        ///2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SAULT + userPassword).getBytes());

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserId(userId);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }

        //账户不能包含特殊字符
        String regex = "[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        ///2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SAULT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            log.info("user login failed, userAccount cannnot match userPassword");
            return null;
        }

        //3.用户脱敏
        User safetyUser = getSafetyUser(user);
        //4.记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);

        return safetyUser;

    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (Objects.isNull(originUser)) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserId(originUser.getUserId());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(UserRoleTypeEnum.ROLE_ORDINARY_USER.getId());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public Boolean userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return Boolean.TRUE;
    }
}




