package com.me.backend.product.mapper;

import com.me.backend.common.Pagination;
import com.me.backend.member.dto.OrderDTO;
import com.me.backend.product.dto.ProductDTO;
import com.me.backend.product.dto.optionDTO;
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

    int modifyProductsCount(Map<String, String> map);

    List<ProductDTO> findModifyProducts(Map<String, Object> map);

    List<ProductDTO> findAllModifyProducts (Map<String, String> map);

    int modifyProduct (ProductDTO product);

    int modifyOptions (List<ProductDTO> options);

    int inputModifyProducts (List<ProductDTO> products);

    int modifyProductImg (Map<String, String> map);

    ProductDTO modifyImg (String id);

    int goodsCount (String id);

    List<ProductDTO> goods (Map<String, Object> map);

    List<ProductDTO> findGoods(String id);

    List<optionDTO> goodsPriceRange(String id);

    int orderCount (String id);

    List<OrderDTO> order (String id);
}
