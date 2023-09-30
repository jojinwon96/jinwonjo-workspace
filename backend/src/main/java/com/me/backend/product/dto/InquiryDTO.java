package com.me.backend.product.dto;

import lombok.Data;

@Data
public class InquiryDTO {
    private int inquiry_id;
    private String cust_id;
    private int product_id;
    private String product_name;
    private String title;
    private String content;
    private String answer;
    private String status;
    private String date;
    private String answer_date;
}
