package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/8 11:22
 * @version: 1.0
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current,limit);
        //根据条件查询所有数据
        List<SysRole> list=sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
    //添加角色
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void updteSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }
}
