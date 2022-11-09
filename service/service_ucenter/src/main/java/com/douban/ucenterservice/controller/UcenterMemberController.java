package com.douban.ucenterservice.controller;


import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.ucenterservice.client.OssClient;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.entity.vo.RegisterVo;
import com.douban.ucenterservice.service.UcenterMemberService;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-29
 */
@RestController
@RequestMapping("/service_ucenter/ucenter-member")
//@CrossOrigin
public class UcenterMemberController extends BaseController {

    //注入OssClient
    @Autowired
    private OssClient ossClient;

    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;

    //用户登录
    @PostMapping ("login")
    public Result loginUser(@RequestBody UcenterMember member){
        //调用service方法实现登录
        //返回token值,使用jwt生成
        String token = null;
        try {
            token = ucenterMemberService.login(member);
        } catch (Exception e) {
            return Result.error().message("登录失败，请检查手机号或密码是否正确");
        }
        return Result.ok().data("token",token);
    }

    //用户注册
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo){
        //注册
        try {
            ucenterMemberService.register(registerVo);
        } catch (Exception e) {
            System.out.print("异常元素:");
            System.out.println(e);
            return Result.error().message("注册失败");
        }
        return Result.ok();
    }

    //根据token解析用户
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request){
        //调用jwt工具类方法,根据request获取头信息，返回id
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0){
            return Result.error().message("查无此人");
        }

        //查询数据库，根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return Result.ok().data("userInfo",member);
    }

    //上传头像
    @PostMapping(value = "uploadimg")
    public Result uploadOssFile(@RequestPart("file") MultipartFile file){
        Result result = ossClient.uploadOssFile(file);
        return result;
    }

}
