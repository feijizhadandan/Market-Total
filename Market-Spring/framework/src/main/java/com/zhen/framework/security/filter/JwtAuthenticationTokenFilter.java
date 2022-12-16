package com.zhen.framework.security.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.zhen.common.utils.JwtUtil;
import com.zhen.common.utils.RedisCache;
import com.zhen.common.utils.RedisUtil;
import com.zhen.framework.security.domain.LoginUser;
import com.zhen.framework.security.domain.User;
import com.zhen.framework.security.service.impl.TokenService;
import io.jsonwebtoken.Claims;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 用于实现认证的过滤器
 * 获取请求中的 jwt 进行鉴别
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*
            过滤情况: 
                1、没有token, 直接放行(只能访问登录接口) (提示用户未登录) 
                2、有token, 但是缓存已经过期, 直接放行 (提示用户未登录)  
                3、有token, 但是token非法, 直接放行 (提示用户未登录) 
                    (后两种情况都是getLoginUser中返回null的)
                4、有token, 验证通过, 将其信息和权限列表加入Holder, 交给后续拦截器统一处理
         */

        // 获取LoginUser：request-->token-->uuid->>LoginUser
        LoginUser loginUser = tokenService.getLoginUser(request);
        
        if(!Objects.isNull(loginUser))
        {
            // 存入SecurityContextHolder(以LoginUser的格式存储在Holder, 所以想删除时也从Holder中移除)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // filter放行 
        filterChain.doFilter(request, response);
        
    }
}
