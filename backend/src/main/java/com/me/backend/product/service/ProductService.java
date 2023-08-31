package com.me.backend.product.service;

import com.me.backend.common.Pagination;
import com.me.backend.product.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    int productCount(Map<String, String> map);

    List<ProductDTO> findProduct(Map<String, Object> map);

    List<ProductDTO> categoryList();

    int inputProduct(ProductDTO product);

    int inputOptions(List<ProductDTO> options);

    int modifyProductCount (Map<String, String> map);

    List<ProductDTO> findModifyProduct(Map<String, Object> map);
}
