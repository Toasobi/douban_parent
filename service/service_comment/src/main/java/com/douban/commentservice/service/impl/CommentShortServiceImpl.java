package com.douban.commentservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commentservice.entity.CommentLong;
import com.douban.commentservice.entity.CommentShort;
import com.douban.commentservice.entity.vo.CommentQuery;
import com.douban.commentservice.mapper.CommentShortMapper;
import com.douban.commentservice.service.CommentShortService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.commonutils.UUid;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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
public class CommentShortServiceImpl extends ServiceImpl<CommentShortMapper, CommentShort> implements CommentShortService {

    @Autowired
    CommentShortServiceImpl commentShortService;

    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;

    @Override
    public Page<CommentShort> QueryWithLimit(long current, long limit, CommentQuery commentQuery,String film_identity,String long_identity) {
        //创建一个page对象
        Page<CommentShort> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<CommentShort> wrapper = new QueryWrapper<>();

        //多条件查询
        //判断条件只是否为空，不为空拼接条件
        if(commentQuery != null) {
            String begin = commentQuery.getBegin();
            if(!StringUtils.isEmpty(begin)){
                wrapper.ge("created_at",begin); //大于等于上映时间，gt是大于
            }
        }
        if(film_identity != null) {
            if (!StringUtils.isEmpty(film_identity)) { //哪部电影下的
                wrapper.eq("film_identity", film_identity);
            }
        }

        if(long_identity != null) {
            if(!StringUtils.isEmpty(long_identity)){
                wrapper.eq("long_identity",long_identity);
            }
        }



        //调用方法实现条件分页查询
        commentShortService.page(page,wrapper);

        return page;
    }


    @Override
    public Result AddShortComment(HttpServletRequest request, CommentShort commentShort) {
        //调用jwt工具类方法,根据request获取头信息，返回id
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0){
            return Result.error().message("您还未登录，请先登录");
        }

        //查询数据库，根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);


        commentShort.setIdentity(UUid.getUUID());
        commentShort.setUserIdentity(member.getIdentity());
        commentShort.setUserAvator(member.getAvatar());
        commentShort.setUserNickname(member.getNickname());


        boolean save = commentShortService.save(commentShort);

        if(save){
            return Result.ok().message("成功添加评论");
        }else{
            return Result.error().message("添加评论失败");
        }
    }
}
