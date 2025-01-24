微服务：不同功能拆分为不同模块

service-user, service-order, service-user



得到每一层分类（不是懒加载）

```java
package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public List<Category> selectOneCategory() {
        return categoryMapper.selectOndCategory();
    }

    @Override
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

```



nacos

service-user, service-order, service-user之间调用是通过远程调用实现的

注册中心类似于中介

1. 启动注册中心服务 nacos
2. 把远程调用两个服务在nacos进行注册
3. 使用openFeign实现远程调用过程

![image-20250124120401192](C:\Users\chenm\AppData\Roaming\Typora\typora-user-images\image-20250124120401192.png)

在D:\schoolneed\nacos-server-2.5.0\nacos\bin>startup.cmd -m standalone启动nacos，访问http://localhost:8848/nacos进入管理界面



网关（作用类似拦截器/过滤器）

统一处理跨域

route路由；predicate 断言（匹配路由关系，路径包含/product都到product里面去）；filter（过滤器）



redis 

1.nosql 数据库

2.基于内存 key-value

3.持久化 rdb和aof

支持多种数据类型

应用：我们的分类很少改变，可以将分类数据放入redis，这样提高效率



spring cache

一个框架，实现基于注解的缓存功能，只需要简单地加一个注解，就可以实现缓存功能，简化缓存代码

@Cacheable 用于查询，将查询结果放到缓存中，实现的效果等于放到redis里面（然后再redis里面找）

@CachePut 用于添加，新增数据同步到cache中

@CacheEvict 清空缓存

在启动类增加@EnableCache
