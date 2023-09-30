package com.me.backend.common;

import com.me.backend.product.dto.InquiryDTO;
import com.me.backend.product.dto.ReviewDTO;
import lombok.Data;

import java.util.List;

@Data
public class PagingView {
    private List<ReviewDTO> reviews;
    private Pagination reviewPagination;
    private List<InquiryDTO> inquiryList;
    private Pagination inquiryPagination;
}
