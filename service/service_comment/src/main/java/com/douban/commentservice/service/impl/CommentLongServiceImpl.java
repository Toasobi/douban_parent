package com.douban.commentservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commentservice.entity.CommentLong;
import com.douban.commentservice.entity.vo.CommentQuery;
import com.douban.commentservice.mapper.CommentLongMapper;
import com.douban.commentservice.service.CommentLongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.commonutils.UUid;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-03
 */
@Service
public class CommentLongServiceImpl extends ServiceImpl<CommentLongMapper, CommentLong> implements CommentLongService {

    @Autowired
    private CommentLongServiceImpl commentLongService;
    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;

    @Override
    public Result AddLongComment(HttpServletRequest request, CommentLong commentLong) {
        //调用jwt工具类方法,根据request获取头信息，返回id
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0){
            return Result.error().message("您还未登录，请先登录");
        }

        //查询数据库，根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);


        commentLong.setIdentity(UUid.getUUID());
        commentLong.setUserIdentity(member.getIdentity());
        commentLong.setUserAvator(member.getAvatar());
        commentLong.setUserNickname(member.getNickname());


        boolean save = commentLongService.save(commentLong);

        if(save){
            return Result.ok().message("成功添加评论");
        }else{
            return Result.error().message("添加评论失败");
        }

    }

    @Override
    public Page<CommentLong> QueryWithLimit(Long current, Long limit, CommentQuery commentQuery,String film_identity) {
        //创建一个page对象
        Page<CommentLong> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<CommentLong> wrapper = new QueryWrapper<>();

        //多条件查询
        //判断条件只是否为空，不为空拼接条件
        String begin = commentQuery.getBegin();

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("created_at",begin); //大于等于上映时间，gt是大于
        }
        if(!StringUtils.isEmpty(film_identity)){
            wrapper.eq("film_identity",film_identity);
        }

        //调用方法实现条件分页查询
        commentLongService.page(page,wrapper);

        return page;
    }

}
