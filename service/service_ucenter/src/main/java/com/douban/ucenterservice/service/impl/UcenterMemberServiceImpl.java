package com.douban.ucenterservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.MD5;
import com.douban.commonutils.UUid;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.entity.vo.RegisterVo;
import com.douban.ucenterservice.mapper.UcenterMemberMapper;
import com.douban.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-29
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录方法
    @Override
    public String login(UcenterMember member) throws Exception {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new Exception("手机号或密码为空");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if(mobileMember == null){ //无该手机号
            throw new Exception("无该手机号");
        }

        //判断密码是否正确
        if(!MD5.MD5(password).equals(mobileMember.getPassword())){
            throw new Exception("密码错误");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()){
            throw new Exception("用户已被禁用");
        }

        //登录成功
        //生成一个token的字符串
        String jwt = JwtUtils.getJwtToken(mobileMember.getId(),mobileMember.getNickname());
        return jwt;
    }

    @Override
    public void register(RegisterVo registerVo) throws Exception {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //名称
        String password = registerVo.getPassword(); //密码

        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
            throw new Exception("用户名或密码不能为空");
        }

        //获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new Exception("验证码无效");
        }

        //手机号不能相同
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        int count = baseMapper.selectCount(wrapper);
        if (count > 0 ){
            throw new Exception("手机号不能相同");
        }

        //添加数据到数据库
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.MD5(password));
        member.setIsDisabled(false);
        member.setIsDeleted(false);
        member.setAvatar("touxiang");
        member.setIdentity(UUid.getUUID());
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getOpenIdMember(String open_id) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",open_id);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
