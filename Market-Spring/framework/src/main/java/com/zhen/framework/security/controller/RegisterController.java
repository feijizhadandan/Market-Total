package com.zhen.framework.security.controller;

import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.security.domain.dto.RegisterInfo;
import com.zhen.framework.security.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterInfo info) {
        return registerService.register(info);
    }

}
