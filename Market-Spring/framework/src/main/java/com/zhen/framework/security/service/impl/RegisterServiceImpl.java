package com.zhen.framework.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.domain.SysUserRole;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.domain.dto.RegisterInfo;
import com.zhen.framework.security.mapper.SysUserRoleMapper;
import com.zhen.framework.security.mapper.UserMapper;
import com.zhen.framework.security.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AjaxResult register(RegisterInfo info) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, info.getUsername());
        // 存在重名用户
        if (userMapper.selectOne(wrapper) != null) return AjaxResult.error("该用户名已被使用");
        User newUser = new User(info.getUsername(), info.getUsername(), info.getEmail(), info.getPhoneNumber(), info.getSex(), passwordEncoder.encode(info.getPassword().trim()));
        // 添加新用户信息
        userMapper.insert(newUser);
        // 添加该用户的角色（2L：普通用户）
        userRoleMapper.insert(new SysUserRole(newUser.getId(), 2L));
        return AjaxResult.success("注册成功");
    }
}
