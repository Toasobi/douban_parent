package com.douban.commentservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commentservice.entity.CommentLong;
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
public interface CommentLongService extends IService<CommentLong> {

    Result AddLongComment(HttpServletRequest request, CommentLong commentLong);

    Page<CommentLong> QueryWithLimit(Long current, Long limit, CommentQuery commentQuery,String film_identity);

}
