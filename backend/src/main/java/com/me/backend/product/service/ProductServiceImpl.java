package com.me.backend.product.service;

import com.me.backend.common.Pagination;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

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
    public int modifyProductCount(Map<String, String> map) {
        return productMapper.modifyProductCount(map);
    }

    @Override
    public List<ProductDTO> findModifyProduct(Map<String, Object> map) {
        return productMapper.findModifyProduct(map);
    }
}
