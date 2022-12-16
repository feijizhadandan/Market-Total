package com.zhen.framework.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.framework.security.domain.User;

public interface UserService extends IService<User> {

    public User selectUserByUsername(String username);
}
