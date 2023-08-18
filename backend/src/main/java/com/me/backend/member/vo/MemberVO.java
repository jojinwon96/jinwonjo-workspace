package com.me.backend.member.vo;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
public class MemberVO {
    private String cust_id;
    private String cust_pwd;
    private String cust_name;
    private String cust_email;
    private String cust_tel;
    private int mileage;
    private String banned;
    private String status;
    private Date enroll_date;
    private Date delete_date;
    private String token;
}
