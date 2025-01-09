package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/8 20:12
 * @version: 1.0
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinioProperties minioProperties;
    @Override
    public String upload(MultipartFile file) {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://127.0.0.1:9000")
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket 'spzx-bucket' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            //获取上传文件名称
            //1 每一个上传文件名称唯一的 uuid生成01.jpg
            //2 根据当前日期对上传文件进行分组20230910

            //20230910/01.jpg
            String dateDir=DateUtil.format(new Date(),"yyyyMMdd");
            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            String fileName = dateDir+"/"+uuid+file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).stream(
                            file.getInputStream(), file.getSize(), -1).build());
            //获取文件在minio中的路径
            String url = "http://127.0.0.1:9000"+"/"+minioProperties.getBucketName()+"/" + fileName;
            return url;
//        } catch (MinioException e) {
//            System.out.println("Error occurred: " + e);
//            System.out.println("HTTP trace: " + e.httpTrace());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidKeyException e) {
//            throw new RuntimeException(e);
//        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
}
