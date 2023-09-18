package com.me.backend.member.dto;

import lombok.Data;

@Data
public class CouponDTO {
    private int coupon_id;
    private int coupon_list_id;
    private String coupon_name;
    private int coupon_price;
}
