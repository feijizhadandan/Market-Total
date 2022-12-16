package com.zhen.framework.utils;

import com.zhen.framework.config.MinioConfig;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 这个工具类比较特殊, 存在需要自动装配的变量(MinioConfig), 因此也加入容器更好
 */
@Component
public class MinioUtil {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient minioClient;

    /**
     * 查看存储Bucket是否存在
     */
    public Boolean bucketExists(String bucketName) {
        Boolean flag;
        try {
            flag = minioClient.bucketExists(BucketExistsArgs
                    .builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    /**
     * 创建存储Bucket
     */
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除存储Bucket
     */
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            List<Bucket> buckets = minioClient.listBuckets();
            return buckets;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 文件上传
     * @param file 文件本身
     * @return 返回 Minio上的文件地址
     */
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        
        // 设置文件名称（唯一，否则会覆盖）
        String fileName = minioConfig.getBucketName() + "_"
                + System.currentTimeMillis() + "_"
                + originalFilename;
        
        try {
            InputStream inputStream = file.getInputStream();
            // 将文件名和文件流传入，并设置好上传Bucket的名称
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            
            minioClient.putObject(objectArgs);
            inputStream.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // 返回的值就是Minio上的文件地址
        return minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }

    /**
     *
     * @param file 文件本身
     * @param bucketName 目标桶名称
     * @return 返回 Minio上的文件地址
     */
    public String uploadToBucket(MultipartFile file, String bucketName) {
        String originalFilename = file.getOriginalFilename();

        // 设置文件名称（唯一，否则会覆盖）
        String fileName = minioConfig.getBucketName() + "_"
                + System.currentTimeMillis() + "_"
                + originalFilename;

        try {
            InputStream inputStream = file.getInputStream();
            // 将文件名和文件流传入，并设置好上传Bucket的名称
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(objectArgs);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 返回的值就是Minio上的文件地址
        return minioConfig.getEndpoint() + "/" + bucketName + "/" + fileName;
    }


    /**
     * 获取临时预览的图片URL
     * @param fileName 文件名称
     * @return 文件临时URL
     */
    public String preview(String fileName) {
        GetPresignedObjectUrlArgs urlArgs = GetPresignedObjectUrlArgs.builder()
                // 设置临时URL访问方式
                .method(Method.GET)
                .bucket(minioConfig.getBucketName())
                // 文件名是Minio系统中的唯一标识
                .object(fileName)
                // 设置连接过期时间
                .expiry(10)
                .build();
        
        try {
            String url = minioClient.getPresignedObjectUrl(urlArgs);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public Boolean remove(String fileName) {
        RemoveObjectArgs objectArgs = RemoveObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .build();
        try {
            minioClient.removeObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 商品图片上传
     * @param file 商品图片文件
     * @return 返回 Minio上的文件地址
     */
    public String uploadProductPhoto(MultipartFile file, String productName) {
        // 设置文件名称（用商品名称命名）
        String fileName = minioConfig.getBucketName() + "_" + productName + ".jpg";

        try {
            InputStream inputStream = file.getInputStream();
            // 将文件名和文件流传入，并设置好上传Bucket的名称
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();

            minioClient.putObject(objectArgs);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 返回的值就是Minio上的文件地址
        return minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }
}
