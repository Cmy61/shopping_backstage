package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysRoleUser;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.Duration;
import java.util.List;
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
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private RedisTemplate <String,String> redisTemplate;
    @Override
    public LoginVo login(LoginDto loginDto) {
        String captcha=loginDto.getCaptcha();
        String key=loginDto.getCodeKey();

        String redisCode=redisTemplate.opsForValue().get("user:validate"+key);

        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode.trim(), captcha.trim())) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        redisTemplate.delete("user:validate"+key);

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

    @Override
    public SysUser getUserInfo(String token) {
        String userJson=redisTemplate.opsForValue().get("user:login:" + token);
        SysUser sysUser=JSON.parseObject(userJson,SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list=sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        //用户名不能重复
        String username=sysUser.getUserName();
        SysUser userbd=sysUserMapper.selectUserInfoByUserName(username);
        if(userbd!=null)
        {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //加密密码
        String password=sysUser.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(password);
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        String username=sysUser.getUserName();
        SysUser userbd=sysUserMapper.selectUserInfoByUserName(username);
        if(userbd!=null)
        {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteById(Integer userId) {
        sysUserMapper.delete(userId);
    }

    @Override
    public void doAssign(AssginRoleDto assignRoleDto) {
        //1. 根据userId删除用户之前分配的数据
        sysRoleUserMapper.deleteByUserId(assignRoleDto.getUserId());
        //2. 重新分配新数据
        List<Long> roleIdList=assignRoleDto.getRoleIdList();
        for(Long roleId:roleIdList)
        {
            sysRoleUserMapper.doAssign(assignRoleDto.getUserId(),roleId);
        }
    }
}