package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/8 16:08
 * @version: 1.0
 */
@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //1. 用户条件分页查询接口
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(
                             @PathVariable(value = "pageNum") Integer pageNum ,
                             @PathVariable(value = "pageSize") Integer pageSize,SysUserDto sysUserDto) {
        PageInfo<SysUser> pageInfo=sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
    //2. 用户添加
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser)
    {
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //3. 用户更新
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser)
    {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //4. 用户删除
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteById(@PathVariable(value = "userId") Integer userId)
    {
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //用户分配角色
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assignRoleDto)
    {
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
