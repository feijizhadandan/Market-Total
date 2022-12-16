package com.zhen.framework.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.framework.security.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
