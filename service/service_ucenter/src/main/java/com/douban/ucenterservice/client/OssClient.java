package com.douban.ucenterservice.client;


import com.douban.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient(name = "service-oss",fallback = OssFeignClient.class) //实现熔断
public interface OssClient {

    //定义调用的方法的路径
    //上传头像方法
    @PostMapping(value = "/filmoss/imgoss" ,consumes = "multipart/form-data")
    public Result uploadOssFile(@RequestPart("file") MultipartFile file);

}
