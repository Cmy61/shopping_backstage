package com.atguigu.spzx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/2/4 10:48
 * @version: 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 1 登录状态，获取当前登录用户id
        //从threadLocal中获取用户信息
        Long userId= AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(userId);
        // 2 判断商品是否存在
        // hash中key是userid，field是skuid，value是sku信息
        //从redis里面获取购物车数据，根据用户id+skuid获取（hash类型key+field）
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        //3 如果购物车存在添加商品 把商品数量相加
        CartInfo cartInfo=null;
        if(cartInfoObj!=null)
        {
            cartInfo=JSON.parseObject(cartInfoObj.toString(),CartInfo.class);
            //数量相加
            Integer skuNum1 = cartInfo.getSkuNum();
            cartInfo.setSkuNum(skuNum1+skuNum);
            //选中商品（默认）
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }
        else{//4如果购物车没有商品，直接商品添加购物车（添加到redis里面）
            cartInfo=new CartInfo();
            //根据skuid获取商品sku信息。远程调用
            ProductSku productSku=productFeignClient.getBySkuId(skuId);
            //设置相关数据到cartInfo
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());

        }
        redisTemplate.opsForHash().put(cartKey,String.valueOf(skuId), JSON.toJSONString(cartInfo));
        //远程调用实现：通过nacos+openfeign实现 根据skuid获取商品sku信息

    }

    @Override
    public List<CartInfo> getCartList() {
        //构建查询redis的key的值，根据用户id得到--threadLocal中得到
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        //根据key从redis里获取hash中的value值 cartInfo
        List<Object> valuesList = redisTemplate.opsForHash().values(cartKey);//得到value而不是field
        //转成cartInfo
        if(!CollectionUtils.isEmpty(valuesList))
        {
            List<CartInfo> cartInfoList = valuesList.stream().map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class)).collect(Collectors.toList());
            return cartInfoList;
        }
        //return
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(Long skuId) {
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        redisTemplate.opsForHash().delete(cartKey,String.valueOf(skuId));
    }
    //选中的商品才进行结算
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        //构建查询的redis里面key值，根据当前userId
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        //判断key中是否包含field
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if(hasKey)
        {
            //根据key+field获取value值
            String cartInfoString = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            //更新选中状态
            CartInfo cartInfo = JSON.parseObject(cartInfoString, CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            //放回hash
            redisTemplate.opsForHash().put(cartKey,String.valueOf(skuId), JSON.toJSONString(cartInfo));
        }
       return;
    }

    @Override
    public void allCheckCart(Integer isChecked) {
        //构建查询的redis里面key值，根据当前userId
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        //判断key中是否包含field
        List<Object> valuesList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(valuesList))
        {
            //转换
            List<CartInfo> cartInfoList = valuesList.stream().map(cartInfoObj -> JSON.parseObject(cartInfoObj.toString(), CartInfo.class)).collect(Collectors.toList());
            //遍历更新全部
            cartInfoList.forEach(cartInfo -> {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(cartKey,String.valueOf(cartInfo.getSkuId()),JSON.toJSONString(cartInfo));//重新存入
            });
        }
        return;
    }

    @Override
    public void clearCart() {
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllCkecked() {
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        List<Object> cartValues = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(cartValues))
        {
            List<CartInfo> collect = cartValues.stream().map(object ->
                    JSON.parseObject(object.toString(), CartInfo.class))
                    .filter(cartInfo->cartInfo.getIsChecked()==1)
                    .collect(Collectors.toList());
            //仅返回选中商品
            return collect;
        }
        return null;
    }

    @Override
    public void deleteChecked() {
        Long id = AuthContextUtil.getUserInfo().getId();
        String cartKey=this.getCartKey(id);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);

        //删除选中商品
        objectList.stream().map(object ->
                        JSON.parseObject(object.toString(), CartInfo.class))
                .filter(cartInfo->cartInfo.getIsChecked()==1)
                .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey,
                        String.valueOf(cartInfo.getSkuId())));
    }

    private String getCartKey(Long userId) {
        return "user:cart:"+userId;
    }
}
