package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/9 13:20
 * @version: 1.0
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        //查询角色分配过的id列表
        List<Long> roleMenuId=sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        Map<String,Object> map=new HashMap<>();
        map.put("sysMenuList",sysMenuList);
        map.put("roleMenuIds",roleMenuId);
        return map;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        //删除角色之前分配过菜单数据
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        //保存分配的数据
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if(menuInfo!=null && menuInfo.size()>0)//角色分配了菜单
        {
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }

    }
}
