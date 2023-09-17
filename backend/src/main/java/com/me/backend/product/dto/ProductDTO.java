package com.me.backend.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private String seller_id;
    private String category_id;
    private String product_id;
    private String option_id;
    private String company_name;
    private String category_name;
    private String product_name;
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
    private String status;
    private String addStatus;

    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    private String uploadFile;

    private String like_id;
}
