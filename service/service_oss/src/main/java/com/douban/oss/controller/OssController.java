package com.douban.oss.controller;


import com.douban.commonutils.Result;
import com.douban.oss.service.OssService;
import com.douban.oss.service.impl.OssServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

//@CrossOrigin //整合前端必备
@RestController
@RequestMapping("/filmoss/imgoss")
public class OssController {

    @Autowired
    private OssServiceImpl ossService;



    //上传头像方法
    @PostMapping
    public Result uploadOssFile(MultipartFile file){
        //获取上传文件 MutipartFile
        //返回路径
        String url = ossService.uploadFileAVA(file);

        return Result.ok().data("url",url);
    }
}
