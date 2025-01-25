package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/24 15:10
 * @version: 1.0
 */
@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value="/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //商品详情接口
    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result item(@PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo , ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit, ProductSkuDto productSkuDto)
    {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

}