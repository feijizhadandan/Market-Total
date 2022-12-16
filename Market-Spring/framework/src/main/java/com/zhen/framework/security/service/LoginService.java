package com.zhen.framework.security.service;

import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    AjaxResult login(User user);

    AjaxResult logout(String token);

    AjaxResult checkLoginStatus(HttpServletRequest request);
}
