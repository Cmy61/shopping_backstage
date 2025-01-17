package com.atguigu.spzx.manager;

import com.atguigu.spzx.manager.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 19:35
 * @version: 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages ={"com.atguigu.spzx"})
@EnableConfigurationProperties(value={MinioProperties.class})
@EnableScheduling//开启定时任务
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
