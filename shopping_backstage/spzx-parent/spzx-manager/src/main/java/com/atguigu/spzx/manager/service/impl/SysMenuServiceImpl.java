package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.utils.MenuHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/9 12:05
 * @version: 1.0
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    //菜单列表
    @Override
    public List<SysMenu> findNodes() {
        //查询所有菜单，返回list集合
        List<SysMenu>list=sysMenuMapper.findAll();
        if(CollectionUtils.isEmpty(list))
        {
            return null;
        }
        //调用工具类，把list封装成要求个数据格式
        List<SysMenu>treeList=MenuHelper.buildTree(list);
        return treeList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    @Override
    public void delete(Long id) {
        //根据当前菜单id查询是否包含子菜单
        int count=sysMenuMapper.selectCountById(id);
        if(count>0)
        {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.delete(id);
    }
}
