package com.zhen.framework.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysUserRole {
    Long userId;
    Long roleId;
}
