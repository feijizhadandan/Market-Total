package com.zhen.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartRecord {
    private Long buyerId;
    private Long productId;
    private Integer count;
}
