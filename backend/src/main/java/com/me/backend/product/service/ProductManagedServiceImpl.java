package com.me.backend.product.service;

import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.dto.optionDTO;
import com.me.backend.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductManagedServiceImpl implements ProductManagedService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public int productCount(Map<String, String> map) {
        return productMapper.productCount(map);
    }

    @Override
    public List<ProductDTO> findProduct(Map<String, Object> map) {
        return productMapper.findProduct(map);
    }

    @Override
    public List<ProductDTO> categoryList() {
        return productMapper.categoryList();
    }

    @Override
    public int inputProduct(ProductDTO product) {
        return productMapper.inputProduct(product);
    }

    @Override
    public int inputOptions(List<ProductDTO> options) {
        return productMapper.inputOptions(options);
    }

    @Override
    public int modifyProductsCount(Map<String, String> map) {
        return productMapper.modifyProductsCount(map);
    }

    @Override
    public List<ProductDTO> findModifyProducts(Map<String, Object> map) {
        return productMapper.findModifyProducts(map);
    }

    @Override
    public List<ProductDTO> findAllModifyProducts(Map<String, String> map) {
        return productMapper.findAllModifyProducts(map);
    }

    @Override
    public int modifyProduct(ProductDTO product) {
        return productMapper.modifyProduct(product);
    }

    @Override
    public int modifyOptions(List<ProductDTO> options) {
        return productMapper.modifyOptions(options);
    }

    @Override
    public int inputModifyProducts(List<ProductDTO> products) {
        return productMapper.inputModifyProducts(products);
    }

    @Override
    public int modifyProductImg(Map<String, String> map) {
        return productMapper.modifyProductImg(map);
    }

    @Override
    public ProductDTO modifyImg(String id) {
        return productMapper.modifyImg(id);
    }

    @Override
    public int goodsCount(String id) {
        return productMapper.goodsCount(id);
    }

    @Override
    public List<ProductDTO> goods(Map<String, Object> map) {
        return productMapper.goods(map);
    }

    @Override
    public List<ProductDTO> findGoods(String id) {
        return productMapper.findGoods(id);
    }

    @Override
    public List<optionDTO> goodsPriceRange(String id) {
        return productMapper.goodsPriceRange(id);
    }

}
