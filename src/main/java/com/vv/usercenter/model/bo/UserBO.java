package com.vv.usercenter.model.bo;

import com.vv.usercenter.converter.EntityBoConverter;
import com.vv.usercenter.mapper.UserRoleTypeEnum;
import com.vv.usercenter.model.entity.User;
import com.vv.usercenter.util.BeanCopyUtils;
import lombok.*;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserBO extends BaseBO{
    private Long id;
    private String username;
    private String userAccount;
    private String avatarUrl;
    private Integer gender;
    private String userPassword;
    private String phone;
    private String email;
    private Integer userStatus;
    private Date createTime;
    private Date updateTime;
    private Integer isDelete;
    private UserRoleTypeEnum userRole;
    private Integer userId;


    public static UserBO fromEntity(User entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        UserBO bo = new UserBO();
        BeanCopyUtils.copy(entity, bo, EntityBoConverter.getConverter());
        return bo;
    }

    public User toEntity() {
        User entity = new User();
        BeanCopyUtils.copy(this, entity, EntityBoConverter.getConverter());
        return entity;
    }
}
