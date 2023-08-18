package com.me.backend.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class member {
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
}
