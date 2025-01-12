package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/11 13:00
 * @version: 1.0
 */
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {
    @Autowired
    private CategoryBrandService categoryBrandService;

    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit, CategoryBrandDto categoryBrandDto)
    {
        PageInfo<CategoryBrand> pageInfo=categoryBrandService.findByPage(page,limit,categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand)
    {
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @PutMapping("/updateById")
    public Result update(@RequestBody CategoryBrand categoryBrand)
    {
        categoryBrandService.update(categoryBrand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id)
    {
        categoryBrandService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
