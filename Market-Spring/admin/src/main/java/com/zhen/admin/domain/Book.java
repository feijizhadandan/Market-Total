package com.zhen.admin.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Book implements Serializable{

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;
    private String type;
    private Integer count;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Character delFlag;

    //private String create_by;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //private String update_by;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    @Version
    private Long version;
}
