package com.zhen.framework.security.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zhen.common.enums.Constants;
import com.zhen.common.utils.IdUtils;
import com.zhen.common.utils.JwtUtil;
import com.zhen.common.utils.RedisUtil;
import com.zhen.framework.security.domain.LoginUser;
import com.zhen.framework.security.domain.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 用于处理token相关服务，并负责将 token-LoginUser 存入redis
 */

@Component
public class TokenService {
    
    @Autowired
    private RedisUtil redisUtil;

    // token过期时间(分钟)
    @Value("${token.expireTime}")
    private int expireTime;
    
    // 自定义token请求头表示
    @Value("${token.header}")
    private String header;

    /**
     * 1s = 1000ms
     */
    private static final Long MILLIS_SECOND = 1000L;

    /**
     * 1min = 1000 * 60
      */
    private static final Long MILLIS_MINUTE = MILLIS_SECOND * 60;

    /**
     * 通过request中的token, 获取redis中的LoginUser
     * @param request 请求
     * @return 该 token 对应的 LoginUser 对象
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求头上的token
        String token = request.getHeader(header);
        if(StringUtils.hasText(token)) {
            try {
                // 解析获得载荷内容
                Claims claims = JwtUtil.parseJWT(token);
                String uuid = claims.getSubject();
                String redisTokenKey = getRedisTokenKey(uuid);
                // redis中没有找到该key
                if(!redisUtil.hasKey(redisTokenKey)) return null;
                
                // 对象中有list的情况，需要这样转换才能反序列化！
                String jsonString  = JSON.toJSONString(redisUtil.get(redisTokenKey));
                LoginUser loginUser = JSON.parseObject(jsonString, LoginUser.class);
                return loginUser;
            } 
            catch (Exception e) {
                // 不抛出异常, 不然匿名访问会被拦截
                //throw new RuntimeException("token非法");
            }
        }
        // 如果没有token, 则返回空
        return null;
    }


    /**
     * 处理首次登录情况
     * @param loginUser
     * @return
     */
    public String handleToken(LoginUser loginUser) {
        // jwt中的载荷token为随机的uuid; 存入redis的key也是该uuid
        String token = IdUtils.fastUUID();
        setLoginUserDetail(loginUser, token);
        putIntoCache(loginUser);
    
        return createToken(token);
    }

    /**
     * 完善LoginUser的相关信息
     * @param loginUser
     */
    public void setLoginUserDetail(LoginUser loginUser, String token) {
        loginUser.setToken(token);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + MILLIS_MINUTE * expireTime);
    }

    /**
     * 将token-LoginUser放入缓存
     * @param loginUser
     * @return
     */
    public void putIntoCache(LoginUser loginUser) {
        String redisTokenKey = getRedisTokenKey(loginUser.getToken());
        redisUtil.set(redisTokenKey, loginUser, expireTime * 60);
    }

    /**
     * 将token-LoginUser从缓存中删除(注销使用)
     * @param loginUser
     */
    public void deleteFromCache(LoginUser loginUser) {
        String redisTokenKey = getRedisTokenKey(loginUser.getToken());
        redisUtil.del(redisTokenKey);
    }

    /**
     * 生成JWT字符串
     * @param claims
     * @return
     */
    public String createToken(String claims) {
        return JwtUtil.createJWT(claims);
    }

    /**
     * 在存入redis的时候，加上前缀，格式为： login_user_key:uuid
     * @param uuid
     * @return
     */
    public String getRedisTokenKey(String uuid) {
        return Constants.LOGIN_USER_KEY + uuid;
    }

    /**
     * 返回前端传来的 Token 对应的用户 User 信息
     * @param request 请求
     * @return User
     */
    public User getLoginUserDetail(HttpServletRequest request) {
        return getLoginUser(request).getUser();
    }
}
