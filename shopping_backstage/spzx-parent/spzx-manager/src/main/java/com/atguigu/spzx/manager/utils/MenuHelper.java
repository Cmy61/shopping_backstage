package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions:封装树形菜单数据
 * @author: cmy
 * @date: 2025/1/9 12:20
 * @version: 1.0
 */

public class MenuHelper {
    //用递归实现
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList)
    {
        //sysMenuList是所有菜单的内容
        //创建list集合，用于封装最终的数据
        List<SysMenu> trees=new ArrayList<>();
        for(SysMenu sysMenu:sysMenuList){
            //找到递归操作的入口，第一层菜单，parent_id=0
            if(sysMenu.getParentId().longValue()==0)
            {
                //递归方法实现找下一层
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        //TODO 完成封装过程
        return trees;
    }
    //递归查找下层
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //sysMenu有属性private list<SysMenu> children封装下一层数据
        sysMenu.setChildren(new ArrayList<>());
        for(SysMenu it:sysMenuList)
        {
            if(sysMenu.getId().longValue()==it.getParentId().longValue())
            {
                //it是sysmenu的下层数据
                sysMenu.getChildren().add(findChildren(it,sysMenuList));
            }
        }
        return sysMenu;
    }
}
