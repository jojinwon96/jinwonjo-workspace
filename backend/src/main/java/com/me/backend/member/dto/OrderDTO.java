package com.me.backend.member.dto;

import com.me.backend.product.dto.ProductDTO;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class OrderDTO {
    private int product_id;
    private int order_id;
    private int order_detail_id;
    private String cust_id;
    private String order_name;
    private String phone;
    private String email;
    private String recipient;
    private String recipient_phone;
    private int post;
    private String addr;
    private String addr_detail;
    private CouponDTO coupon;
    private int mileage;
    private int optionTotal;
    private int price;
    private int count;
    private Date order_date;
    private List<CartDTO> orderList;
    private String company_name;
    private String product_name;
    private int option_id;
    private String option_name1;
    private String option_content1;
    private String option_name2;
    private String option_content2;
    private String img;
    private String option_price;
    private String uploadFile;
    private String status;
    private String refundStatus;
}
