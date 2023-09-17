package com.me.backend.member.dto;

import lombok.Data;

@Data
public class CartDTO {
    private String cart_id;
    private String cust_id;
    private String product_id;
    private String option_id;
    private String company_name;
    private String product_name;
    private String option_name1;
    private String option_content1;
    private String option_name2;
    private String option_content2;
    private int option_price;
    private int discount;
    private int price;
    private int count;
    private int maxCount;
    private String img;
    private String uploadFile;
}
