package com.zhen.admin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProductDto {
    private Long id;
    private String productName;
    private Double productPrice;
    private Integer productCount;
    private String productIntroduction;
    private Boolean isShow;
    private MultipartFile photoFile;
}
