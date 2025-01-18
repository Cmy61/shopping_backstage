package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/17 16:25
 * @version: 1.0
 */
@Aspect
@Component
public class LogAspect {
    //环绕通知
    @Around(value="@annotation(sysLog)") //加了@sysLog 的方法就会执行环绕通知
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {
        String title=sysLog.title();
        int businessType = sysLog.businessType();
        System.out.println("title:"+title+"::businessType:"+ businessType);
        //执行业务方法
        Object proceed;
        try {
            proceed = joinPoint.proceed();//业务方法
            System.out.println("在业务方法后执行");
        } catch (Throwable e) {
            throw new RuntimeException();
        }
        return proceed;
    }
}
