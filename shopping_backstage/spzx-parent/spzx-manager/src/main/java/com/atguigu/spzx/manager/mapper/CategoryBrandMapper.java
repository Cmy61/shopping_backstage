package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/11 13:05
 * @version: 1.0
 */
@Mapper
public interface CategoryBrandMapper {
    public List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void update(CategoryBrand categoryBrand);

    void delete(Long id);
}
