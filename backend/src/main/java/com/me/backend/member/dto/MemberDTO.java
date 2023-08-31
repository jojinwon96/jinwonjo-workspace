package com.me.backend.member.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class MemberDTO {
    private String cust_id;
    private String cust_pwd;
    private String cust_name;
    private String cust_email;
    private String cust_tel;
    private int post;
    private String address1;
    private String address2;
    private int mileage;
    private String banned;
    private String status;
    private Date enroll_date;
    private Date delete_date;
    private String token;

    private String seller_id;
    private String seller_pwd;
    private String seller_number;
    private String seller_name;
    private String company_name;
    private String seller_tel;
}
