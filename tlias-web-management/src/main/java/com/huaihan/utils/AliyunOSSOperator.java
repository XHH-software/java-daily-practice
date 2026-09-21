package com.huaihan.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AliyunOSSOperator {

/* // 通过@Value注解一个属性一个属性的注入
   // 北京地域
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.region}")
    private String region;*/

    // 定义一个实体类，使用@ConfigurationProperties(prefix = "aliyun.oss")注解批量注入
    @Autowired
    private AliyunoSSProperties aliyunoSSProperties;

    /**
     * SDK V2 上传文件 byte数组
     * @param content 文件字节数组
     * @param originalFilename 原始文件名
     * @return 文件外网访问url
     */
    public String upload(byte[] content, String originalFilename) {

        String endpoint = aliyunoSSProperties.getEndpoint();
        String region = aliyunoSSProperties.getRegion();
        String bucketName = aliyunoSSProperties.getBucketName();

        // 【V2官方凭证】自动读取环境变量 OSS_ACCESS_KEY_ID / OSS_ACCESS_KEY_SECRET
        EnvironmentVariableCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();

        // 目录 yyyy/MM
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));

        // 安全截取后缀，防止无后缀文件名报错
        String suffix = "";
        if (originalFilename != null && originalFilename.lastIndexOf(".") > 0) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID() + suffix;
        String objectName = dir + "/" + newFileName;

        // V2 SDK构建客户端，try-with-resources自动关闭client（V2推荐写法，不用手动shutdown）
        try (OSSClient ossClient = OSSClient.newBuilder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .endpoint(endpoint)
                .build()) {

            PutObjectRequest putReq = PutObjectRequest.newBuilder()
                    .bucket(bucketName)
                    .key(objectName)
                    .body(BinaryData.fromBytes(content))
                    .build();

            ossClient.putObject(putReq);

        } catch (Exception e) {
            throw new RuntimeException("OSS上传失败", e);
        }

        // 返回访问地址
        return "https://" + bucketName + "." + endpoint.replace("https://", "") + "/" + objectName;
    }
}
