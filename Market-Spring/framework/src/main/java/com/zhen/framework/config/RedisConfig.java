package com.zhen.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.xml.parsers.ParserConfigurationException;

@Configuration
public class RedisConfig {

    // 我们通过使用 RedisTemplate 来往 redis 中存放数据，因此我们需要对redisTemplate进行序列化配置，才能让存入的数据被序列化
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        // 默认键类型为String
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Fastjson 序列化器
        FastJson2JsonRedisSerializer fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);

        // String的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        /**
         * 可以理解为，redis中的不同键值，可以采用不同的序列化策略，也就是提供不同的 ”序列化器“
         * 如果不指定序列化器，则默认使用JDK的序列化策略
         *
         * 一般情况下，Key都采用String的序列化策略；Value都采用Json的策略，其中Json的序列化有不同的序列化器，比如jackson，Fastjson
         */

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value采用Json序列化方式（jackson）
        template.setValueSerializer(fastJson2JsonRedisSerializer);
        // hash的value也采用Json序列化方式（jackson）
        template.setHashValueSerializer(fastJson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }
}
