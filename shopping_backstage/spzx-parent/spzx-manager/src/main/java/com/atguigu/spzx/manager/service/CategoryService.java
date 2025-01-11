package com.atguigu.spzx.manager.service;


import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);

    void exportData(HttpServletResponse httpServletResponse);
}
