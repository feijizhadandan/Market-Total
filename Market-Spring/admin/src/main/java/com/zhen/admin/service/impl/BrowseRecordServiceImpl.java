package com.zhen.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhen.admin.domain.BrowseRecord;
import com.zhen.admin.mapper.BrowseRecordMapper;
import com.zhen.admin.service.BrowseRecordService;
import com.zhen.admin.vo.BrowseRecordVo;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BrowseRecordServiceImpl extends ServiceImpl<BrowseRecordMapper, BrowseRecord> implements BrowseRecordService {

    @Autowired
    private BrowseRecordMapper bbMapper;

    @Override
    public AjaxResult getBrowseRecord(HttpServletRequest request) {
        List<BrowseRecordVo> browseRecords = bbMapper.getBrowseRecordList();
        return AjaxResult.success(browseRecords);
    }
}
