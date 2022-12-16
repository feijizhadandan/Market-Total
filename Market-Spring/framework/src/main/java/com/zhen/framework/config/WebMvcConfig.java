package com.zhen.framework.config;

import com.zhen.framework.common.JacksonObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SpringMVC 配置类
 * WebMvcConfigurer 能在不影响自动装配的情况下, 进行自定义配置
 * WebMvcConfigurationSupport 会使自动配置失效(不推荐)
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // knife4j 静态资源映射
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 积木报表 静态资源映射
        registry.addResourceHandler("/jmreport/desreport_/**").addResourceLocations("classpath:/static/jmreport/desreport_/");
    }

    /**
     * 序列化总结：
     *      1、如果使用JDK序列化方式，序列化结果可读性较差，并且必须实现Serializable接口
     *      2、推荐使用Json序列化方式，Json序列化只会序列化键、值，不包含内容信息，因此在反序列时需要提供对应的类型。
     *      
     *      精度丢失问题：对象中的Long属性是可以正常序列化的，但是Long属性在前端JS接收后，表达不出那么高精度的值，因此需要对Long类型使用String的序列化方式，序列化结果就是带双引号的字符串
     *      LocalDateTime的显示问题：如果直接进行Json序列化，会导致结果格式为："yyyy-MM-ddTHH:mm:ss"(中间有个T)，因此也需要自定义序列化结果的格式
     *      两者都在JacksonObjectMapper中进行自定义，并将其添加到消息转换器中
     */

    /**
     * 添加自定义的Jackson序列化器, 用于自定义序列化LocalDateTime的格式 和 将Long类型转换成字符串类型
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //消息转换器,将service返回的数据转换成json
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //追加到转换器容器中，index为0表示优先级最高
        converters.add(0, messageConverter);
    }
}
