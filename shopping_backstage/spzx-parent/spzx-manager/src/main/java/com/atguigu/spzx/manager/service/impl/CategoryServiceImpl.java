package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/10 15:09
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findCategoryList(Long id) {
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(id);
        if(!CollectionUtils.isEmpty(categoryList)) {

            // 遍历分类的集合，获取每一个分类数据
            categoryList.forEach(item -> {

                // 查询该分类下子分类的数量
                int count = categoryMapper.selectCountByParentId(item.getId());
                if(count > 0) {
                    item.setHasChildren(true);
                } else {
                    item.setHasChildren(false);
                }

            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try{
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            //1 设置头信息  Content-disposition让文件以下列方式打开
            response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");
            //2 调用mapper方法查询所有分类
            List<Category> categoryList=categoryMapper.findAll();
            List<CategoryExcelVo> categoryExcelVoList=new ArrayList<>();
            for(Category category:categoryList)
            {
                CategoryExcelVo categoryExcelVo=new CategoryExcelVo();
                //把category里的值获取并舍之道vo中
//                Long id = category.getId();
////                categoryExcelVo.setId(id);
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }
            //3 调easyexcel的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);

        }catch (Exception e)
        {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

    }

}
