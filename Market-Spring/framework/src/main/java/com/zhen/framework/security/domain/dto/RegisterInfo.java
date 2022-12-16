package com.zhen.framework.security.domain.dto;

import lombok.Data;

/**
 * 接收来自前端的注册信息
 */
@Data
public class RegisterInfo {
    String username;
    String password;
    String phoneNumber;
    String email;
    Character sex;
}
