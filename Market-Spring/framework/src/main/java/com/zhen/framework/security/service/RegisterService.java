package com.zhen.framework.security.service;

import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.domain.dto.RegisterInfo;

public interface RegisterService {
    AjaxResult register(RegisterInfo info);
}
