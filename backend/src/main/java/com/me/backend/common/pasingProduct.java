package com.me.backend.common;

import com.me.backend.product.dto.ProductDTO;
import lombok.Data;

import java.util.List;

@Data
public class pasingProduct {
    private List<ProductDTO> products;
    private Pagination pagination;
    private List<ProductDTO> options;
}
