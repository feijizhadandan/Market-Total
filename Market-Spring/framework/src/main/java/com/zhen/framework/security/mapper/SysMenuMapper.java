package com.zhen.framework.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.framework.security.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectPermsByUserId(Long userid);
}
