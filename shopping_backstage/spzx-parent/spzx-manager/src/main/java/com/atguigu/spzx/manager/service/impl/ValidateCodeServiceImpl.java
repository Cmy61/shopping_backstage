package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/7 20:43
 * @version: 1.0
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        CircleCaptcha circleCaptcha=CaptchaUtil.createCircleCaptcha(150,48,4,2);
        String code=circleCaptcha.getCode();
        String imageBase64=circleCaptcha.getImageBase64();
        String key= UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set("user:validate"+key,code, Duration.ofMinutes(5));
        ValidateCodeVo validateCodeVo=new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
