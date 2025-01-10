package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 19:37
 * @version: 1.0
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto)
    {
        LoginVo loginVo=sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
    @Autowired
    private ValidateCodeService validateCodeService;

    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo=validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token) {
        SysUser sysUser=sysUserService.getUserInfo(token);
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader(name="token") String token)
    {
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //查询用户可以操作的菜单
    @GetMapping("/menus")
    public Result menus()
    {
        List<SysMenuVo> list=sysMenuService.findMenusByUserId();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
}
