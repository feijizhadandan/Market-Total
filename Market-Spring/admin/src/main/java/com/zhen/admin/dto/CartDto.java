package com.zhen.admin.dto;

import lombok.Data;

/**
 * 接收前端传来的购物车的信息
 */
@Data
public class CartDto {
    private Long productId;
    private Integer count;
}
