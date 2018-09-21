package com.adu.springboot.config;

import com.adu.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 表示配置类
 */
@Configuration
public class MyAppConfig {

    @Bean
    public HelloService helloService() {
        System.out.println("helloService...");
        return new HelloService();
    }
}
