package com.zhen.framework.security.controller;

import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口
 * 通过AuthenticationManager的authenticate方法，来进行用户身份校验
 * AuthenticationManager需要在SecurityConfig中配置将AuthenticationManager注入容器，方便在Service层自动装配使用
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody User user) {
        return loginService.login(user);
    }

    @PostMapping("/out")
    public AjaxResult logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        return loginService.logout(token);
    }

    /**
     * 验证该用户(Token)的登录状态
     * @param request
     * @return
     */
    @PostMapping("/check")
    public AjaxResult checkLoginStatus(HttpServletRequest request) {
        return loginService.checkLoginStatus(request);
    }
}
