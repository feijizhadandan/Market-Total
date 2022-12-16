package com.zhen.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowseRecord {
    private Long productId;
    private Long buyerId;
    private LocalDateTime actionTime;
}
