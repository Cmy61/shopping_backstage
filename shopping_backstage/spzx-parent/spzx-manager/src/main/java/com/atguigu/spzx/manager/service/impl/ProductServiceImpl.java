package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/13 18:12
 * @version: 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper ;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page , limit) ;
        List<Product> productList =  productMapper.findByPage(productDto) ;
        return new PageInfo<>(productList);
    }

    @Override
    public void save(Product product) {
        //1 保存商品基本信息 product 表
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save();
        //2 获取商品sku列表合集，保存sku信息，product_sku表
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0;i<productSkuList.size();i++)
        {
            ProductSku productSku=productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

            productSku.setProductId(product.getId());               // 设置商品id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }
        //3 保存商品详情数据 produtct_detail
        ProductDetails productDetails=new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }
}
