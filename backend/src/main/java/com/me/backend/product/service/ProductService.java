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

    int modifyProductsCount (Map<String, String> map);

    List<ProductDTO> findModifyProducts(Map<String, Object> map);

    List<ProductDTO> findAllModifyProducts (Map<String, String> map);

    int modifyProduct (ProductDTO product);

    int modifyOptions (List<ProductDTO> options);

    int inputModifyProducts (List<ProductDTO> products);

    int modifyProductImg (Map<String, String> map);

    ProductDTO modifyImg (String id);
}
