package com.douban.ucenterservice.controller;


import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.commonutils.UUid;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import com.douban.ucenterservice.utils.ConstantWxUtils;
import com.douban.ucenterservice.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;

    //生成微信扫描二维码
    //请求固定地址得到二维码
    @GetMapping("login")
    public String getWxCode(){
        //固定地址，后面拼接参数
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //文档里面要求redirect_url进行URLEncode编码
        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            String redirectUrl = URLEncoder.encode(redirect_url,"utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("生成二维码失败");
        }

        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_REDIRECT_URL,
                "Toasobi" //原样传递
        );
        //请求一个地址
        //上面的restController会默认交给spring管理并返回数据，咱们不需要返回，直接请求，换成controller注解
        return "redirect:"+url;
    }

    //获取扫码人信息，添加数据
    //跳转到返回路径后会在url上传递信息
    //code，类似于手机验证码，随机唯一的值
    @GetMapping("callback")
    public String callback(String code,String state) {


        try {
            //1.获取code值，临时票据，类似于验证码
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //拼接三个参数
            String access = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            //2.请求地址，得到access_token和openid
            //使用http.client(先引依赖)发送请求，得到返回结果
            String accessTokenInfo =  HttpClientUtils.get(access);

//            System.out.println("accessTokenInfo:"+accessTokenInfo);

            //从accessTokenInfo字符串中获取出来两个值
            //使用gson解析出这两个值
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //先查一次数据库
            //加数据之前先做一次判断,根据open_id做判断
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);

            if(member == null){ //无该数据，加入

                //3.拿着得到access_token 和 open_id,再去请求微信提供的固定的地址，获取扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接两个参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );

                //发送请求，得到结果
                String userInfo = HttpClientUtils.get(userInfoUrl);

//            System.out.println(userInfo);

                //获取返回userInfo字符串扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo,HashMap.class);
                String nickname = (String)userInfoMap.get("nickname");
                String headimgurl = (String)userInfoMap.get("headimgurl");

                //扫码人信息添加进数据库当中
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setIdentity(UUid.getUUID());
                ucenterMemberService.save(member);
            }

            //使用jwt根据member对象生成字符串
            String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
            //最后返回首页面,通过路径传递token字符串
            return "redirect:http://localhost:3000?token="+token;

            //System.out.println(state);

        } catch (Exception e) {
            System.out.print("请求异常,错误:");
            System.out.println(e);
            return null;
        }

    }

}
