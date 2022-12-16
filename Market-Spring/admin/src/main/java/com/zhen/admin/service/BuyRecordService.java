package com.zhen.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.admin.domain.BuyRecord;
import com.zhen.common.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;

public interface BuyRecordService extends IService<BuyRecord> {
    AjaxResult getBuyRecord(HttpServletRequest request);
}
