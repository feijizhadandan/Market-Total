package com.zhen.framework.security.service.impl;

import com.zhen.common.domain.AjaxResult;
import com.zhen.common.utils.RedisUtil;
import com.zhen.framework.security.domain.LoginUser;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 系统用户登录登出服务类
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录
     * @param user 登录信息
     * @return 响应消息
     */
    @Override
    public AjaxResult login(User user) {
        /*
            1、将用户名密码封装成 UsernamePasswordAuthenticationToken (Authentication子类)
            2、通过 authenticationManager的authenticate 方法, 传入(1)的对象, 进行身份验证
                2.1、authenticate方法会调用UserDetailsService的实现类进行验证
                2.2、自己写一个UserDetailsService的实现类UserDetailsServiceImpl, 其结果应当返回UserDetails的子类(LoginUser)
                2.3、LoginUser中还应当封装了权限列表 List<String> permissions, 在UserDetailsServiceImpl中查询赋值
            3、认证结果：
                认证失败：抛出异常
                认证通过：取出其中的LoginUser, 调用handleToken，将其存入redis，并返回一个token字符串
            4、返回前端
         */
        
        // 通过 AuthenticationManager(在SecurityConfig中注入到了容器中) 进行用户身份认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        // 该方法会调用UserDetailServiceImpl 中的 loadUserByUsername方法
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 认证没通过：给出对应提示
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("账号或密码错误,登录失败");
        }

        // 认证通过：从认证结果中中将登录信息取出
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        

        // 将结果封装成map返回
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenService.handleToken(loginUser));
        return AjaxResult.success(tokenMap);
    }

    /**
     * 注销
     * @return 响应消息
     */
    @Override
    public AjaxResult logout(String token) {
        // 先获取LoginUser
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        
        tokenService.deleteFromCache(loginUser);
        
        return AjaxResult.success("注销成功");
    }

    /**
     * 验证登录状态
     * @param request 请求体
     * @return 是否登录
     */
    @Override
    public AjaxResult checkLoginStatus(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        return loginUser == null ? AjaxResult.error("用户未登录") : AjaxResult.success("用户已登录");
    }
}
