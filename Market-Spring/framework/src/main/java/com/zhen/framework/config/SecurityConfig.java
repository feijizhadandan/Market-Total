package com.zhen.framework.config;

import com.zhen.framework.security.exceptionHandler.AccessDeniedHandlerImpl;
import com.zhen.framework.security.exceptionHandler.AuthenticationEntryPointImpl;
import com.zhen.framework.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启权限控制
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    
    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * BCrypt 密码生成、校验器
     * @return 密码生成、校验器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 将AuthenticationManager注入容器，用于用户身份认证
     * @return 返回 AuthenticationManager 对象（注入IOC容器）
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 放行登录接口
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭CSRF
                .csrf().disable()
                // 不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                /**
                 * 是否允许访问，是在SpringSecurity过滤器链最后的一个FilterSecurityInterceptor拦截器中进行判断
                 * 查看自身线程的Holder中是否存在LoginUser信息(在Filter中如果通过验证就会存进去)：
                 *      如果没有LoginUser信息，说明是匿名访问
                 *      如果有，则进行权限控制(存进Holder时，存了一份权限列表进去)
                 */
                // 对于登录、注册、的接口，允许匿名访问; minio允许匿名访问
                .antMatchers("/login", "/register", "/minio/**").anonymous()
                // 对于积木报表资源，允许所有访问; 对于商城、搜索，允许所有访问
                .antMatchers("/jmreport/**",  "/product/buyer",  "/product/buyer/search/**").permitAll()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()                
                // 其他都需要认证
                .anyRequest().authenticated();

        /**
         * 区分登录和认证的区别：
         *      登录：在Filter中查询到redis中没有该token信息，或没有携带token，那么只能访问LoginController接口
         *           在LoginServiceImpl中实现用户名密码校验，校验成功则放入redis，并返回token字符串
         *           
         *      认证：携带的token，在Filter中解析后，发现redis中存在认证信息，
         *           从redis中取出对应的LoginUser，LoginUser中包含权限信息，在Filter中放入Holder，即可完成权限的认证。
         */
        
        // 还要将JwtFilter注册到SpringSecurity中, 用于认证是否已登录
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        // 配置异常处理器
        http.exceptionHandling()
                // 配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 配置授权失败处理器
                .accessDeniedHandler(accessDeniedHandler);
    }
}
