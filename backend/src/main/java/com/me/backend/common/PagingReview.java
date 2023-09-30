package com.me.backend.common;

import com.me.backend.product.dto.ReviewDTO;
import lombok.Data;

import java.util.List;

@Data
public class PagingReview {
    private List<ReviewDTO> reviews;
    private Pagination pagination;
}
