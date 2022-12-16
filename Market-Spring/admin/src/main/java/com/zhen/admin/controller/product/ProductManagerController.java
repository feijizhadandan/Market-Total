package com.zhen.admin.controller.product;

import com.zhen.admin.domain.Product;
import com.zhen.admin.dto.ProductDto;
import com.zhen.admin.service.ProductService;
import com.zhen.common.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/product/manager")
public class ProductManagerController {

    @Autowired
    private ProductService productService;

    /**
     * 查看所有商品（包括不展示的、数量为0的）
     * @return 响应消息
     */
    @GetMapping()
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult getAllProduct() {
        return productService.getAllProduct();
    }

    /**
     * 查看某个商品的详细信息
     * @param id 商品 id
     * @return 相应消息
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult getProductDetail(@PathVariable Long id) {
        return productService.getProductDetail(id);
    }

    /**
     * 添加新的商品
     * @param product 商品信息
     * @param request 请求体
     * @return 响应消息
     */
    @PostMapping()
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult addNewProduct(@RequestBody Product product, HttpServletRequest request) {
        return productService.addNewProduct(product, request);
    }

    /**
     * 更新商品信息
     * @param product 新的商品信息
     * @param request 请求体
     * @return 响应消息
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult updateProduct(@RequestBody Product product, HttpServletRequest request) {
        return productService.updateProduct(product, request);
    }

    /**
     * 上传商品图片
     * @param photoFile 图片文件
     * @param id 商品 id
     * @param request 请求
     * @return 响应消息
     */
    @PostMapping("/photo/{id}")
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult upload(@RequestPart MultipartFile photoFile, @PathVariable Long id, HttpServletRequest request) {
        return productService.uploadProductPhoto(photoFile, id, request);
    }

    /**
     * 逻辑删除某商品
     * @param id 删除的商品id
     * @param request 请求体
     * @return 响应消息
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('system:manager')")
    public AjaxResult deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        return productService.deleteProduct(id, request);
    }

}
