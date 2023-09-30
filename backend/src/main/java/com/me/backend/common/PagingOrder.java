package com.me.backend.common;

import com.me.backend.member.dto.OrderDTO;
import lombok.Data;

import java.util.List;

@Data
public class PagingOrder {
    private List<OrderDTO> order;
    private Pagination pagination;
}
