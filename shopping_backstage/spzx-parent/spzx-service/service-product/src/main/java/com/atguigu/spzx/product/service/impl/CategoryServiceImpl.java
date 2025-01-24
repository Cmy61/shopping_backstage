package com.atguigu.spzx.product.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.simpleframework.xml.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/24 11:10
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public List<Category> selectOneCategory()
    {
        //查询redis是否包含所有一级分类
        String categoryOneJson=redisTemplate.opsForValue().get("category:one");
        //如果redis包含所有一级分类，直接返回
        if(StringUtils.hasText(categoryOneJson))
        {
            //categoryOneJson 字符串转换List<Category>
            List<Category> existCategoryList = JSON.parseArray(categoryOneJson, Category.class);
            return existCategoryList;
        }
        //如果redis没有包含所有一级分类，查询数据库，把数据库查询内容返回，并查询内容放到redis里面
        List<Category> categoryList = categoryMapper.selectOndCategory();
        //查询到的内容放入redis
        redisTemplate.opsForValue().set("category:one",JSON.toJSONString(categoryList), Duration.ofDays(7));
        return categoryList;
    }
    @Override
    @Cacheable(value="category",key="'all'")//category::all
    public List<Category> findCategoryTree() {
        // 查询所有分类 返回list集合
        List<Category> allCategoryList=categoryMapper.findAll();
        //遍历所有分类list集合 通过条件parentid=0得到所有一级分类
        List<Category> oneCategoryList = allCategoryList.stream().filter(item -> item.getParentId()
                        .longValue() == 0)
                .collect(Collectors.toList());


        //遍历所有一级分类list集合，条件判断：id=parentid 得到一级下面二级
                oneCategoryList.forEach(oneCategory->{
                    List<Category> twoCategoryList=allCategoryList.stream()
                            .filter(item->item.getParentId()==oneCategory.getId())
                            .collect(Collectors.toList());
                    oneCategory.setChildren(twoCategoryList);

                    twoCategoryList.forEach(twoCategory->{
                        List<Category> threeCategoryList=allCategoryList.stream()
                                .filter(item->item.getParentId()==twoCategory.getId())
                                .collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                });
        //遍历所有二级分类，条件判断：id=parentid
        return oneCategoryList;
    }
}
