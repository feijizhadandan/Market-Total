package com.zhen.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

// 扩大扫描范围, 扫描别的模块;
// 排除积木报表中的 MongoDB启动依赖
@SpringBootApplication(scanBasePackages= {"com.zhen", "org.jeecg.modules.jmreport"}, exclude={MongoAutoConfiguration.class})
@MapperScan({"com.zhen.framework.security.mapper", "com.zhen.admin.mapper"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
