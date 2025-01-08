package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 19:38
 * @version: 1.0
 */
@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);
}
