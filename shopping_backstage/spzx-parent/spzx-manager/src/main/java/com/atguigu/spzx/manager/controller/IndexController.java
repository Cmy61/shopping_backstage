package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
