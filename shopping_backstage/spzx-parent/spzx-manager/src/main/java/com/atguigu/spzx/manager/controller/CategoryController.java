package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/10 15:09
 * @version: 1.0
 */
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //分类列表，每次查询一层数据
    @GetMapping("/findByParentId/{id}")
    public Result findCategoryList(@PathVariable Long id)
    {
        List<Category> list=categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    //导入
    @PostMapping("importData")
    public Result importData(MultipartFile file)
    {
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //导出
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse httpServletResponse)
    {
        categoryService.exportData(httpServletResponse);
    }


}
