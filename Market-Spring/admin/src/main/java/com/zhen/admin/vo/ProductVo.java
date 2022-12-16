package com.zhen.admin.vo;

import com.zhen.admin.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductVo extends Product {
    private String createName;
    private String updateName;
}
