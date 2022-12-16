package com.zhen.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Product {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long code;
    private String productName;
    private Double productPrice;
    private Integer productCount;
    private String productIntroduction;
    private Boolean isShow;
    private String photoUrl;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Character delFlag;

    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    @Version
    private Long version;

}
