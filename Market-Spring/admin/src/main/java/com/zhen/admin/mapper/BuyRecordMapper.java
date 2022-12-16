package com.zhen.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.admin.domain.BuyRecord;
import com.zhen.admin.vo.BrowseRecordVo;
import com.zhen.admin.vo.BuyRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BuyRecordMapper extends BaseMapper<BuyRecord> {
    List<BuyRecordVo> getBuyRecordList();
}
