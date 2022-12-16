package com.zhen.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyRecord {
    private Long buyerId;
    private Long productId;
    private Double productPrice;
    private Integer count;
    private Double totalPrice;
    private LocalDateTime actionTime;
}
