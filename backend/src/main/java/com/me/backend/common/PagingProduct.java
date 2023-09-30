package com.me.backend.common;

import com.me.backend.product.dto.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class PagingProduct {
    private List<ProductDTO> products;
    private List<ProductDTO> options;
    private Pagination pagination;
}
