package com.me.backend.product.mapper;

import com.me.backend.common.Pagination;
import com.me.backend.product.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductMapper {

    int productCount(Map<String, String> map);
    List<ProductDTO> findProduct(Map<String, Object> map);

    List<ProductDTO> categoryList();

    int inputProduct(ProductDTO product);

    int inputOptions(List<ProductDTO> options);

    int modifyProductCount(Map<String, String> map);

    List<ProductDTO> findModifyProduct(Map<String, Object> map);
}
