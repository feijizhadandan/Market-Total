package com.zhen.admin.controller;

import com.zhen.admin.service.BrowseRecordService;
import com.zhen.admin.service.BuyRecordService;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private BrowseRecordService browseService;

    @Autowired
    private BuyRecordService buyService;

    /**
     * 获取用户的浏览记录
     * @param request 请求
     * @return 响应消息
     */
    @GetMapping("/browse")
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult getBrowseRecord(HttpServletRequest request) {
        return browseService.getBrowseRecord(request);
    }

    /**
     * 获取用户的购买记录
     * @param request 请求
     * @return 响应消息
     */
    @GetMapping("/buy")
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult getBuyRecord(HttpServletRequest request) {
        return buyService.getBuyRecord(request);
    }

}
