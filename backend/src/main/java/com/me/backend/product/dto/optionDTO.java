package com.me.backend.product.dto;

import lombok.Data;

@Data
public class optionDTO {
    private String option_id;
    private String product_id;
    private String option_name1;
    private String option_content1;
    private String option_name2;
    private String option_content2;
    private int option_price;
    private int discount;
    private int price;
    private int stock;
    private int maxCount;
    private String main;
}
