package com.me.backend.product.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String product_id;
    private String product_name;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private int product_price;
    private int sale;
    private String option_name;
    private String option_content;
    private int option_price;
    private int stock;
    private String category_id;
    private String category_name;
}
