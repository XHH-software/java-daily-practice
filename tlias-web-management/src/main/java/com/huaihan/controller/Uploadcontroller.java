package com.huaihan.controller;

import com.huaihan.pojo.Result;
import com.huaihan.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class Uploadcontroller {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @PostMapping("/upload")
    public Result uplode(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());

        // 将文件交给OSS存储管理
        String url = aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        log.info("文件上传，URL为：{}",url);

        return Result.success(url);
    }
}
