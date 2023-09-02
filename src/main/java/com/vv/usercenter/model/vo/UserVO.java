package com.vv.usercenter.model.vo;

import com.vv.usercenter.mapper.UserRoleTypeEnum;
import com.vv.usercenter.model.bo.UserBO;
import com.vv.usercenter.model.entity.User;
import com.vv.usercenter.util.BeanCopyUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Slf4j
public class UserVO {
    private Long id;
    private String username;
    private String userAccount;
    private String avatarUrl;
    private Integer gender;
    private String phone;
    private String email;
    private Integer userStatus;
    private Date createTime;
    private Integer isDelete;
    private Integer userRole;
    private Integer userId;
    public static UserVO fromBo(UserBO bo) {
        if (Objects.isNull(bo)) {
            return null;
        }

        UserVO vo = new UserVO();
        BeanCopyUtils.copy(bo, vo);
        vo.setUserId(bo.getUserRole().getId());
        return vo;
    }
}
