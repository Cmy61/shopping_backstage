package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/9 12:04
 * @version: 1.0
 */
@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu)
    {
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //修改
    @PutMapping("/updateById")
    public Result update(@RequestBody SysMenu sysMenu)
    {
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //删除
    @DeleteMapping("/removeById/{id}")
    public Result delete(@PathVariable("id")Long id)
    {
        sysMenuService.delete(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //菜单列表 树形结构
    @GetMapping("/findNodes")
    public Result findNodes()
    {
        List<SysMenu> list=sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
