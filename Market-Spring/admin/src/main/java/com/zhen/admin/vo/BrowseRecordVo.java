package com.zhen.admin.vo;

import com.zhen.admin.domain.BrowseRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseRecordVo extends BrowseRecord {
    private String productName;
    private String buyerName;
}
