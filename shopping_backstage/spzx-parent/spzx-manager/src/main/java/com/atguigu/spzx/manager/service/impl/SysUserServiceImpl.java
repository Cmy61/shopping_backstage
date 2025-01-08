package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.UUID;

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

    @Autowired
    private RedisTemplate <String,String> redisTemplate;
    @Override
    public LoginVo login(LoginDto loginDto) {
        String userName=loginDto.getUserName();
        SysUser sysUser=sysUserMapper.selectUserInfoByUserName(userName);
        if(sysUser==null)
        {
//            throw new RuntimeException("用户名不存在");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        String database_password=sysUser.getPassword();
        String input_password=loginDto.getPassword();
        input_password=DigestUtils.md5DigestAsHex(input_password.getBytes());

        if(!database_password.equals(input_password))
        {
//            throw new RuntimeException("密码不正确");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        String token= UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set("user:login:" + token, JSON.toJSONString(sysUser), Duration.ofDays(7));

        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        // 返回
        return loginVo;
    }
}