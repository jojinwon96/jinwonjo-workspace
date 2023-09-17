package com.me.backend.member.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String address_id;
    private String cust_id;
    private String addr_name;
    private String name;
    private int post;
    private String addr;
    private String addr_detail;
    private String addr_default;
    private String phone;
}
