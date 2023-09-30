package com.me.backend.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class goodsView {
    private List<ProductDTO> goods;
    private List<optionDTO> options;
    private List<ReviewDTO> reviews;
}
