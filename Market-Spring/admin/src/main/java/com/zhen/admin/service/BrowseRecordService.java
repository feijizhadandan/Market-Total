package com.zhen.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.admin.domain.BrowseRecord;
import com.zhen.common.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;

public interface BrowseRecordService extends IService<BrowseRecord> {
    AjaxResult getBrowseRecord(HttpServletRequest request);
}
