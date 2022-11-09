package com.douban.ucenterservice.client;

import com.douban.commonutils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OssFeignClient implements OssClient{
    //调用失败后的容错策略
    @Override
    public Result uploadOssFile(MultipartFile file) {
        return Result.error().message("上传头像失败");
    }
}
