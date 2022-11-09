package com.douban.msmservice.controller;

import com.douban.commonutils.Result;
import com.douban.msmservice.service.MsmService;
import com.douban.msmservice.service.MsmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/film/msm")
public class MsmController {

    @Autowired
    private MsmServiceImpl msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone){
        //1.先从redis中获取验证码，如果获取不到则进行发送
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            redisTemplate.delete(phone);
        }
        //生成一个随机值，传递给阿里云进行发送
        code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.send(param,phone);
        if (isSend){
            //把code存进redis中，设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Result.ok();
        }else {
            return Result.error().message("发送失败");
        }
    }

}
