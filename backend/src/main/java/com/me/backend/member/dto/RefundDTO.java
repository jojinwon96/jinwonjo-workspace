package com.me.backend.member.dto;

import lombok.Data;

@Data
public class RefundDTO {
    private int refund_id;
    private int order_detail_id;
    private String reason;
    private String content;
    private String status;
    private String date;
    private String processing_date;

    private int product_id;
    private String product_name;
    private String option_name1;
    private String option_content1;
    private String option_name2;
    private String option_content2;
    private int price;
    private int count;
    private String img;
    private String uploadFile;
}
