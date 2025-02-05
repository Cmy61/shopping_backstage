package com.atguigu.spzx.cart;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/2/4 10:42
 * @version: 1.0
 */
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})//开启远程调用
@EnableUserLoginAuthInterceptor //将信息放到threadlocal中
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 排除数据库的自动化配置，Cart微服务不需要访问数据库
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class , args) ;
    }

}