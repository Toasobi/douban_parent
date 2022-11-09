package com.douban.wannaservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableFeignClients
@ComponentScan(basePackages = {"com.douban"})
@EnableDiscoveryClient //nacos注册
@MapperScan("com.douban.commentservice.mapper")
public class WannaApplication {
    public static void main(String[] args) {
        SpringApplication.run(WannaApplication.class, args);
    }
}
