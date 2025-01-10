package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/9 13:19
 * @version: 1.0
 */
@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    //1. 查询所有菜单和查询角色分配过菜单id列表
    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable(value = "roleId") Long roleId) {
        Map<String,Object> map=sysRoleMenuService.findSysRoleMenuByRoleId(roleId);//返回map取值更方便
        return Result.build(map , ResultCodeEnum.SUCCESS)  ;
    }
    //2. 保存角色分配菜单数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto)
    {
       sysRoleMenuService.doAssign(assginMenuDto);
       return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
