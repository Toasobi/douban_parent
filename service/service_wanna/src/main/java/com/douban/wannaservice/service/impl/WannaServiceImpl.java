package com.douban.wannaservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import com.douban.wannaservice.service.WannaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.douban.wannaservice.entity.Wanna;
import com.douban.wannaservice.mapper.WannaMapper;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-06
 */
@Service
public class WannaServiceImpl extends ServiceImpl<WannaMapper, Wanna> implements WannaService {
    @Autowired
    private WannaServiceImpl wannaService;

    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;
    @Override
    public Result addWanna(HttpServletRequest request, String film_identity) {
        //调用jwt工具类方法,根据request获取头信息，返回id
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0){
            return Result.error().message("您还未登录，请先登录");
        }

        //查询数据库，根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        String userIdentity = member.getIdentity();

        QueryWrapper<Wanna> wrapper = new QueryWrapper<>();

        wrapper.eq("user_identity",userIdentity);
        wrapper.eq("film_identity",film_identity);

        //是否已经存在该数据
        Wanna existed =wannaService.getOne(wrapper);
        if(existed != null){
           existed.setStatus(0);
        }

        Wanna wanna = new Wanna();
        wanna.setUserIdentity(userIdentity);
        wanna.setFilmIdentity(film_identity);
        wanna.setStatus(0);
        boolean isTrue = wannaService.save(wanna);
        if(isTrue){
            return Result.ok();
        }
        return null;
    }

    @Override
    public Result addDWanna(HttpServletRequest request, String film_identity) {
        //调用jwt工具类方法,根据request获取头信息，返回id
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0){
            return Result.error().message("您还未登录，请先登录");
        }

        //查询数据库，根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        String userIdentity = member.getIdentity();

        QueryWrapper<Wanna> wrapper = new QueryWrapper<>();

        wrapper.eq("user_identity",userIdentity);
        wrapper.eq("film_identity",film_identity);

        //是否已经存在该数据
        Wanna existed =wannaService.getOne(wrapper);
        if(existed != null){
            existed.setStatus(1);
        }

        Wanna wanna = new Wanna();
        wanna.setUserIdentity(userIdentity);
        wanna.setFilmIdentity(film_identity);
        wanna.setStatus(1);
        boolean isTrue = wannaService.save(wanna);
        if(isTrue){
            return Result.ok();
        }
        return null;
    }
}
