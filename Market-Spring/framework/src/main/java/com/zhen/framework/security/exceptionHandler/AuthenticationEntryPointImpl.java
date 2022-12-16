package com.zhen.framework.security.exceptionHandler;

import com.alibaba.fastjson2.JSON;
import com.zhen.common.domain.AjaxResult;
import com.zhen.common.enums.HttpStatus;
import com.zhen.common.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    private static final String DEFAULT_MSG = "Full authentication is required to access this resource";

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        String msg;
        msg = DEFAULT_MSG.equals(e.getMessage()) ? "用户未登录" : e.getMessage();
        
        // 参数e就是捕获的异常, 可以从里面获取异常信息
        AjaxResult ajaxResult = AjaxResult.error(HttpStatus.UNAUTHORIZED,msg);
        String jsonString = JSON.toJSONString(ajaxResult);
        // 返回异常信息
        WebUtils.renderString(httpServletResponse, jsonString);
    }
}
