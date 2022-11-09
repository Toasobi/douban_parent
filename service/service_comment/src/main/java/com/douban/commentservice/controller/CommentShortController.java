package com.douban.commentservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commentservice.entity.CommentLong;
import com.douban.commentservice.entity.CommentShort;
import com.douban.commentservice.entity.vo.CommentQuery;
import com.douban.commentservice.service.impl.CommentLongServiceImpl;
import com.douban.commentservice.service.impl.CommentShortServiceImpl;
import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-03
 */
@RestController
@RequestMapping("/commentservice/comment-short")
@Api(description="短评功能")
public class CommentShortController extends BaseController {
    //拿到service对象
    @Autowired
    private CommentShortServiceImpl commentShortService;
    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;

    //添加短评功能
    @ApiOperation(value = "添加短评功能")
    @PostMapping("addShortComment")
    public Result addLong(HttpServletRequest request, @RequestBody CommentShort commentShort) {

        Result result = commentShortService.AddShortComment(request, commentShort);

        return result;

    }

    //根据id删除短评功能
    @ApiOperation(value = "删除短评功能")
    @DeleteMapping("deleteLongComment/{id}")
    public Result deleteLongComment(HttpServletRequest request, @ApiParam(name = "id", value = "长评id", required = true) @PathVariable int id) {
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0) {
            return Result.error().message("您还未登录，请先登录");
        }

        boolean remove = commentShortService.removeById(id);
        if (remove) {
            return Result.ok();
        } else {
            return Result.error();
        }

    }

    //修改短评功能
    @ApiOperation(value = "修改短评功能")
    @PostMapping("update")
    public Result updateLongComment(HttpServletRequest request, @RequestBody CommentShort commentShort) {
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0) {
            return Result.error().message("您还未登录，请先登录");
        }

        boolean flag = commentShortService.updateById(commentShort);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //带条件分页查看电影下短评
    @ApiOperation(value = "查看短评功能")
    @GetMapping("search/{current}/{limit}/{film_identity}")
    public Result SearchLongComment(@ApiParam(name = "film_identity", value = "电影identity", required = false) @PathVariable String film_identity, @ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current, @ApiParam(name = "limit", value = "单页展示条数", required = true)
    @PathVariable long limit, @RequestBody(required = false) CommentQuery commentQuery) {

        Page<CommentShort> pageComment = commentShortService.QueryWithLimit(current, limit, commentQuery, film_identity,null);

        long total = pageComment.getTotal(); //总记录数
        List<CommentShort> records = pageComment.getRecords(); //数据list集合

        return Result.ok().data("total", total).data("records", records);
    }

    //分页展示某长评下的所有短评
    @ApiOperation(value = "查看长评下短评功能")
    @GetMapping("search/{current}/{limit}/{long_identity}")
    public Result SearchLongComment(@ApiParam(name = "long_identity", value = "长评identity", required = false) @PathVariable String long_identity, @ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current, @ApiParam(name = "limit", value = "单页展示条数", required = true) @PathVariable long limit) {
        Page<CommentShort> pageComment = commentShortService.QueryWithLimit(current, limit, null, null,long_identity);

        long total = pageComment.getTotal(); //总记录数
        List<CommentShort> records = pageComment.getRecords(); //数据list集合

        return Result.ok().data("total", total).data("records", records);
    }

    //点赞系统
    @ApiOperation(value = "为评论点赞!")
    @PostMapping("update/{id}")
    public Result IsLikeCommment(@ApiParam(name = "id", value = "短评id", required = false) @PathVariable Integer id){
        CommentShort commentShort = commentShortService.getById(id);
        Integer isLike = commentShort.getIsLike();
        isLike = isLike + 1;
        commentShort.setIsLike(isLike);
        return Result.ok();
    }
}
