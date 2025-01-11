package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/11 12:24
 * @version: 1.0
 */
@Mapper
public interface BrandMapper {
    public List<Brand> findByPage() ;

    void save(Brand brand);

    void update(Brand brand);

    void deleteById(Long id);
}
