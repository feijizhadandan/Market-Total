package com.zhen.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhen.admin.domain.Product;
import com.zhen.admin.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    List<Product> searchByKeyword(String keyword);

    List<ProductVo> getAllProductDetail();

    ProductVo getProductDetail(Long id);

}
