package com.douban.oss.service.impl;

import com.douban.oss.service.OssService;
import com.douban.oss.utils.ConstantPropertiesUtils;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;

import java.io.IOException;
import java.io.InputStream;

import java.util.UUID;


@Service
public class OssServiceImpl implements OssService {
    public  String uploadFileAVA(MultipartFile file){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "exampledir/exampleobject.txt";
//        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        String filePath= "D:\\localpath\\examplefile.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            //调用oss方法实现上传
            //第二个参数 上传oss文件路径和文件名称 获取一下
            String filename = file.getOriginalFilename();

            //在文件名中添加uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            filename = uuid+filename;

            //把文件按照日期分类
            //获取当前日期
            String datePath = new DateTime().toString("YYYY/MM/dd");
            filename = datePath+"/"+filename;

            // 创建PutObject请求。
            ossClient.putObject(bucketName, "file_img"+"/"+filename, inputStream);

            //把上传文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return null;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("Caught an IOException");
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

    }
    }

