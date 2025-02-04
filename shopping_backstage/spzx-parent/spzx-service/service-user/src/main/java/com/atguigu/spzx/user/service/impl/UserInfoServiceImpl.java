package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/2/2 16:26
 * @version: 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String username=userRegisterDto.getUsername();
        String password=userRegisterDto.getPassword();
        String nickName=userRegisterDto.getNickName();
        String code=userRegisterDto.getCode();

        String redisCode=redisTemplate.opsForValue().get(username);
        if(!redisCode.equals(code))
        {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        UserInfo userInfo=userInfoMapper.selectByUsername(username);
        if(userInfo!=null)
        {
            //存在相同用户
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);
        redisTemplate.delete(username);
    }
}
