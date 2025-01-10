package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysUser;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/10 11:43
 * @version: 1.0
 */
// com.atguigu.spzx.utils
public class AuthContextUtil {

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>() ;

    // 定义存储数据的静态方法
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 定义获取数据的方法
    public static SysUser get() {
        return threadLocal.get() ;
    }

    // 删除数据的方法
    public static void remove() {
        threadLocal.remove();
    }

}