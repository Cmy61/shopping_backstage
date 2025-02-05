package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/24 11:10
 * @version: 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Override
    public List<ProductSku> selectProductSkuBySal() {
        return productSkuMapper.selectProductSkuBySale();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> list=productSkuMapper.findByPage(productSkuDto);

        return new PageInfo<>(list);
    }
    //商品详情接口
    @Override
    public ProductItemVo item(Long skuId) {
        //创建vo对象，用于封装最终数据
        ProductItemVo productItemVo=new ProductItemVo();
        //根据skuid获取sku信息
        ProductSku productSku=productSkuMapper.getById(skuId);
        //根据第二部获取sku，从sku获取productId，获取商品信息
        Long productId=productSku.getProductId();
        Product product=productMapper.getById(productId);
        //productId，获取商品详情信息
        ProductDetails productDetails=productDetailsMapper.getByProductId(productId);
        //封装map集合==商品规格对应商品skuId信息
        Map<String,Object> skuSpecValueMap=new HashMap<>();
        List<ProductSku> productSkuList=productSkuMapper.findByProductId(productId);
        productSkuList.forEach(item->{
            skuSpecValueMap.put(item.getSkuSpec(),item.getId());
        });
        //把需要数据封装到productItemVo
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        //封装详情图片
        String imageUrls=productDetails.getImageUrls();
        String[] split = imageUrls.split(",");
        List<String >list= Arrays.asList(split);
        productItemVo.setDetailsImageUrlList(list);
        //封装轮播图 list集合
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));

        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        ProductSku byId = productSkuMapper.getById(skuId);
        return byId;
    }
}
