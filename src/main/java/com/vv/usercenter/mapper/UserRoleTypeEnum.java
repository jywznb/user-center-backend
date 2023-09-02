package com.vv.usercenter.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum UserRoleTypeEnum {
    ROLE_SYSTEM_ADMIN("system_admin", "系统管理员", 1),
    ROLE_ORDINARY_USER("ordinary_user", "普通用户", 1);

    private static Map<String, UserRoleTypeEnum> ROLE_MAP = new HashMap<>();

    static {
        for (UserRoleTypeEnum role: UserRoleTypeEnum.values()) {
            ROLE_MAP.put(role.getCode(), role);
        }
    }

    public static UserRoleTypeEnum findByCode(String code) {
        return ROLE_MAP.get(code);
    }

    private String code;
    private String name;
    private Integer id;


}

