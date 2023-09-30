package com.me.backend.common;

import com.me.backend.product.dto.InquiryDTO;
import lombok.Data;

import java.util.List;

@Data
public class PagingInquiry {
    private List<InquiryDTO> inquiryList;
    private Pagination pagination;
}
