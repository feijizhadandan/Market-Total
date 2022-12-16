package com.zhen.admin.vo;

import com.zhen.admin.domain.BrowseRecord;
import com.zhen.admin.domain.BuyRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyRecordVo extends BuyRecord {
    private String productName;
    private String buyerName;
}
