package com.zhen.admin.controller;

import com.zhen.common.domain.AjaxResult;
import com.zhen.framework.utils.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// @Slf4j
@Api(tags = "minio-file-controller")
@RestController
@RequestMapping("/minio")
public class MinioFileController {
    
    @Autowired
    private MinioUtil minioUtil;
    
    @ApiOperation("上传文件到默认bucket,返回url")
    @PostMapping("/upload")
    public AjaxResult upload(@RequestPart MultipartFile file) {
        String filename = minioUtil.upload(file);
        return AjaxResult.success(filename);
    }

    @ApiOperation("上传文件到指定bucket,返回url")
    @PostMapping("/upload/{bucketName}")
    public AjaxResult uploadToBucket(@RequestPart MultipartFile file, @PathVariable String bucketName) {
        String filename = minioUtil.uploadToBucket(file, bucketName);
        return AjaxResult.success(filename);
    }
    
    @ApiOperation("预览图片")
    @GetMapping("/preview")
    public AjaxResult preview(@RequestParam String filename) {
        String url = minioUtil.preview(filename);
        return AjaxResult.success(url);
    }
    
    @ApiOperation("删除图片")
    @DeleteMapping
    public AjaxResult delete(@RequestParam String filename) {
        Boolean flag = minioUtil.remove(filename);
        if (flag) return AjaxResult.success("删除成功");
        else return AjaxResult.error("删除失败");
    }
}
