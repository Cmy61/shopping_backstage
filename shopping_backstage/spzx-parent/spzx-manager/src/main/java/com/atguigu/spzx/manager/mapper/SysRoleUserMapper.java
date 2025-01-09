package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/9 11:04
 * @version: 1.0
 */
@Mapper
public interface SysRoleUserMapper {

    void deleteByUserId(Long userId);

    void doAssign(Long userId, Long roleId);
}
