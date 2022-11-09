package com.douban.commentservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commentservice.entity.CommentLong;
import com.douban.commentservice.entity.CommentShort;
import com.baomidou.mybatisplus.extension.service.IService;
import com.douban.commentservice.entity.vo.CommentQuery;
import com.douban.commonutils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-03
 */
public interface CommentShortService extends IService<CommentShort> {

    Page<CommentShort> QueryWithLimit(long current, long limit, CommentQuery commentQuery,String film_identity,String long_identity);

    Result AddShortComment(HttpServletRequest request, CommentShort commentShort);
}
