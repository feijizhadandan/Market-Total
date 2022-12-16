package com.zhen.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.admin.domain.CartRecord;
import com.zhen.admin.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartRecordMapper extends BaseMapper<CartRecord> {
    List<CartVo> getAllRecord(Long buyerId);
}
