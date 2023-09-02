package com.vv.usercenter.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 用户登录请求体
 *
 * @author jyw
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 8066215859518267223L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;

}
