package com.douban.commentservice.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commentservice.entity.CommentLong;
import com.douban.commentservice.entity.vo.CommentQuery;
import com.douban.commentservice.service.impl.CommentLongServiceImpl;
import com.douban.commentservice.service.impl.CommentShortServiceImpl;
import com.douban.commonutils.JwtUtils;
import com.douban.commonutils.Result;
import com.douban.commonutils.UUid;
import com.douban.ucenterservice.entity.UcenterMember;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/commentservice/comment-long")
@Api(description="长评功能")
public class CommentLongController extends BaseController {
    //拿到service对象
    @Autowired
    private CommentLongServiceImpl commentLongService;
    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;



    //添加长评功能
    @ApiOperation(value = "添加长评功能")
    @PostMapping("addLongComment")
    public Result addLong(HttpServletRequest request,@RequestBody CommentLong commentLong){

        Result result =commentLongService.AddLongComment(request,commentLong);

        return result;

    }

    //根据id删除长评功能
    @ApiOperation(value = "删除长评功能")
    @DeleteMapping ("deleteLongComment/{id}")
    public Result deleteLongComment(HttpServletRequest request,@ApiParam(name = "id",value = "长评id", required = true)@PathVariable int id){
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0) {
            return Result.error().message("您还未登录，请先登录");
        }
        boolean remove = commentLongService.removeById(id);
        if (remove){
            return Result.ok();
        }else {
            return Result.error();
        }

    }

    //修改长评功能
    @ApiOperation(value = "修改长评功能")
    @PostMapping ("update")
    public Result updateLongComment(HttpServletRequest request,@RequestBody CommentLong commentLong){
        Integer memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == 0) {
            return Result.error().message("您还未登录，请先登录");
        }
        boolean flag = commentLongService.updateById(commentLong);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    //带条件分页查看长评
    @ApiOperation(value = "查看长评功能")
    @GetMapping ("search/{current}/{limit}/{film_identity}")
    public Result SearchLongComment(@ApiParam(name = "film_identity",value = "电影identity", required = false)@PathVariable String film_identity,@ApiParam(name = "current",value = "当前页", required = true)@PathVariable long current,@ApiParam(name = "limit",value = "单页展示条数", required = true)
    @PathVariable long limit, @RequestBody(required = false) CommentQuery commentQuery){

        Page<CommentLong> pageComment = commentLongService.QueryWithLimit(current,limit,commentQuery,film_identity);

        long total = pageComment.getTotal(); //总记录数
        List<CommentLong> records = pageComment.getRecords(); //数据list集合

        return Result.ok().data("total",total).data("records",records);
    }


}
