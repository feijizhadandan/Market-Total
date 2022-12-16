package com.zhen.framework.security.exceptionHandler;

import com.alibaba.fastjson2.JSON;
import com.zhen.common.domain.AjaxResult;
import com.zhen.common.enums.HttpStatus;
import com.zhen.common.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限不足处理类
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        
        // 参数e就是捕获的异常, 可以从里面获取异常信息
        AjaxResult ajaxResult = AjaxResult.error(HttpStatus.FORBIDDEN, e.getMessage());
        String jsonString = JSON.toJSONString(ajaxResult);
        // 返回异常信息
        WebUtils.renderString(httpServletResponse, jsonString);
    }
}
