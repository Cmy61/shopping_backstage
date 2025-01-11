package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/11 12:23
 * @version: 1.0
 */
@RestController
@RequestMapping(value="/admin/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    //列表
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,@PathVariable Integer limit)
    {
        PageInfo<Brand> pageInfo=brandService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
//    @GetMapping("/findAll")
//    public Result findAllBrand()
//    {
//
//    }
    //添加
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand)
    {
        brandService.save(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //修改
    @PutMapping("/updateById")
    public Result update(@RequestBody Brand brand)
    {
        brandService.update(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id)
    {
        brandService.deleteById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
