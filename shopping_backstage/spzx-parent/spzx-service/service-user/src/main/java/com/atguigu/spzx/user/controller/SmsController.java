package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/2/2 16:03
 * @version: 1.0
 */
@RestController
@RequestMapping("api/user/sms")
public class SmsController {
    @Autowired
    private SmsService smsService ;

    @GetMapping(value = "/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone) {
        smsService.sendCode(phone);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
