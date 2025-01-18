package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/8 11:21
 * @version: 1.0
 */
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    //current 当前页 limit 每页限制记录数
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto)
    {
        PageInfo<SysRole> pageInfo=sysRoleService.findByPage(sysRoleDto,current,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);//pageInfo包含每页数据
    }

    //角色添加
    @Log(title="角色管理:添加",businessType=1)
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole)
    {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //角色修改
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole)
    {
        sysRoleService.updteSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //角色删除
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId)
    {
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    // 查询所有角色
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long userId) {
        Map<String, Object> resultMap = sysRoleService.findAll(userId);
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }


}
