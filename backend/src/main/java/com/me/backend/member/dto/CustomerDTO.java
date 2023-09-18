package com.me.backend.member.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private MemberDTO member;
    private List<CouponDTO> couponList;
}
