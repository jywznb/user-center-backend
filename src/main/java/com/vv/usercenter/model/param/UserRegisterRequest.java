package com.vv.usercenter.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 用户注册请求体
 *
 * @author jyw
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -6205654359346056582L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String userId;
}
