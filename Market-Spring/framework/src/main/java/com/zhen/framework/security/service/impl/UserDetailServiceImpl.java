package com.zhen.framework.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhen.framework.security.domain.LoginUser;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.mapper.SysMenuMapper;
import com.zhen.framework.security.mapper.UserMapper;
import com.zhen.framework.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 替代原有的UserDetailsService(从内存查数据), 自定义成 从数据库中查用户信息
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户信息
        User user = userService.selectUserByUsername(username);
        // 没有查询到该用户
        if(Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        // 查看该用户状态
        else if (user.getStatus() == 1) {
            throw new RuntimeException("该账号已被停用");
        }

        // 验证该用户存在后, 封装成LoginUser返回
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(User user){
        // 查询数据库获得用户权限
        List<String> permissions = sysMenuMapper.selectPermsByUserId(user.getId());
        return new LoginUser(user, permissions);
    }
}
