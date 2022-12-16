package com.zhen.admin.dto;

import lombok.Data;

/**
 * 购物车中支付的条目
 *     商品ID、商品价格、商品数量
 */
@Data
public class PayProductDto {
    private Long productId;
    private Double productPrice;
    private Integer count;
}
