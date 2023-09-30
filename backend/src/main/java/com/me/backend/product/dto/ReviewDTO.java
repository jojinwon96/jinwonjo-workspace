package com.me.backend.product.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private int review_id;
    private String cust_id;
    private int product_id;
    private String product_name;
    private String title;
    private String content;
    private int rate;
    private String img;
    private String product_img;
    private String status;
    private String date;
    private String uploadFile;
}
