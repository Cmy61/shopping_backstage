package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 19:38
 * @version: 1.0
 */
@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    void delete(Integer userId);
}
