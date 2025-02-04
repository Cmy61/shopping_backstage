package com.atguigu.spzx.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/2/2 16:01
 * @version: 1.0
 */

@SpringBootApplication
@ComponentScan(basePackages={"com.atguigu.spzx"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}