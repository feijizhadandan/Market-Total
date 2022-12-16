package com.zhen.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhen.admin.domain.Product;
import com.zhen.admin.dto.ProductDto;
import com.zhen.admin.dto.UpdateProductDto;
import com.zhen.common.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ProductService extends IService<Product> {
    AjaxResult getAllProduct();

    AjaxResult getProductDetail(Long id);

    AjaxResult addNewProduct(Product product, HttpServletRequest request);

    AjaxResult updateProduct(Product product, HttpServletRequest request);

    AjaxResult uploadProductPhoto(MultipartFile photoFile, Long productId, HttpServletRequest request);

    AjaxResult deleteProduct(Long id, HttpServletRequest request);

    AjaxResult getShowProduct();

    AjaxResult getShowProductDetail(Long id, HttpServletRequest request);

    void addBrowseRecord(Product product, HttpServletRequest request);

    AjaxResult searchProduct(String keyword, HttpServletRequest request);
}
