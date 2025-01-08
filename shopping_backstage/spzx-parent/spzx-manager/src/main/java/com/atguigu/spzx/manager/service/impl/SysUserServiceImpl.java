package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 19:38
 * @version: 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService
{
    @Autowired
    private SysUserMapper sysUserMapper;
}