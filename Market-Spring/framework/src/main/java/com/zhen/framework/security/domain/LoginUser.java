package com.zhen.framework.security.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 从数据库查询到用户信息后，需要封装成UserDetails类型的数据返回给上一层处理器
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户基本信息
     */
    private User user;

    /**
     * 用户权限列表
     */
    private List<String> permissions;

    /**
     * 用户认证token
     */
    private String token;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;


    /**
     * 用户权限列表(注入到SpringSecurity的类型)
     */
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;


    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    /**
     * 通过该函数将权限列表(String)转换成SpringSecurity中的权限信息
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 因为返回值类型要是一个值类型为GrantedAuthority的集合，因此需要进行类型转换
        // 使用文件流的方式进行转换
        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
    }

    /**
     * 通过这个getter获取LoginUser中的User的密码,然后拿去比对
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
