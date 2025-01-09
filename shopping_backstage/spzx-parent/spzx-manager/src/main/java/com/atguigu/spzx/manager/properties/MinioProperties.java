package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @descriptions:
 * @author: cmy
 * @date: 2025/1/8 20:32
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix="spzx.minio")
public class MinioProperties {

    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;

}
