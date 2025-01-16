package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku productSku);

    List<ProductSku> findProductSkuByProductId(Long id);

    void updateById(Product product);

    void deleteByProductId(Long id);
}
