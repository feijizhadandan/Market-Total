package com.zhen.admin.controller;

import com.zhen.admin.dto.CartDto;
import com.zhen.admin.dto.PayProductDto;
import com.zhen.admin.service.CartRecordService;
import com.zhen.admin.vo.CartVo;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRecordService cartRecordService;

    /**
     * 获取该用户购物车中的信息
     * @param request 请求
     * @return 响应消息
     */
    @GetMapping
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult getBuyerCart(HttpServletRequest request) {
        return cartRecordService.getBuyerCart(request);
    }

    /**
     * 添加购物车记录（可能购物车中已经有该物品，因此需要分情况处理）
     * @param addCartDto 购物车信息
     * @param request 请求
     * @return 返回消息
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult addCartRecord(@RequestBody CartDto addCartDto, HttpServletRequest request) {
        return cartRecordService.addCartRecord(addCartDto, request);
    }

    /**
     * 修改购物车记录（修改数量）
     * @param changeCartDto 修改内容
     * @param request 请求
     * @return 响应消息
     */
    @PutMapping
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult changeCartRecord(@RequestBody CartDto changeCartDto, HttpServletRequest request) {
        return cartRecordService.changeCartRecord(changeCartDto, request);
    }

    /**
     * 删除购物车的某条记录
     * @param id 商品id
     * @param request 请求
     * @return 响应消息
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult deleteCartRecord(@PathVariable Long id, HttpServletRequest request) {
        return cartRecordService.deleteCartRecord(id, request);
    }

    /**
     * 用户在购物车中支付购买选中物品
     * @param payProductList 购物车中被选中的商品列表
     * @param request 请求
     * @return 响应消息
     */
    @PostMapping("/pay")
    @PreAuthorize("hasAuthority('system:buy')")
    public AjaxResult payProduct(@RequestBody List<CartVo> payProductList, HttpServletRequest request) {
        return cartRecordService.payProduct(payProductList, request);
    }

}
