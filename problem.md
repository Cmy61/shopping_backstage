



categorybrand部分-修改

<img src="C:\Users\chenm\AppData\Roaming\Typora\typora-user-images\image-20250112142226570.png" alt="image-20250112142226570" style="zoom:33%;" />

如果只修改品牌，分类部分会显示有问题（前端要解决）







public void save(Product product) {
    //1 保存商品基本信息 product 表
    product.setStatus(0);
    product.setAuditStatus(0);
    productMapper.save(product);
    //2 获取商品sku列表合集，保存sku信息，product_sku表
    List<ProductSku> productSkuList = product.getProductSkuList();
    
    for(int i=0;i<productSkuList.size();i++)
    {
        ProductSku productSku=productSkuList.get(i);
        productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

​        productSku.setProductId(product.getId());               // 设置商品id
​        productSku.setSkuName(product.getName() + productSku.getSkuSpec());
​        productSku.setSaleNum(0);                               // 设置销量
​        productSku.setStatus(0);
​        productSkuMapper.save(productSku);
​    }
​    //3 保存商品详情数据 produtct_detail
​    ProductDetails productDetails=new ProductDetails();
​    Long id = product.getId();
​    System.*out*.println("Product ID: " + product.getId());
​    productDetails.setProductId(product.getId());
​    productDetails.setImageUrls(product.getDetailsImageUrls());
​    productDetailsMapper.save(productDetails);
}

添加之后无法获取product.getId()D:\cmy\learning\shopping_backstage\shopping_backstage\spzx-parent\spzx-manager\src\main\java\com\atguigu\spzx\manager\service\impl\ProductServiceImpl.java





077 078没有完成 因为需要用到前面的内容