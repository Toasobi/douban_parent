package com.douban.wannaservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.douban.commonutils.Result;
import com.douban.ucenterservice.service.impl.UcenterMemberServiceImpl;
import com.douban.wannaservice.service.impl.WannaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-06
 */
@RestController
@RequestMapping("/com/douban/wannaservice/wanna")
public class WannaController extends BaseController {
    @Autowired
    WannaServiceImpl wannaService;

    @Autowired
    private UcenterMemberServiceImpl ucenterMemberService;

    //用户添加想看列表
    @ApiOperation(value = "用户添加想看功能")
    @PostMapping("addWanna/{film_identity}")
    public Result addWanna(HttpServletRequest request, @ApiParam(name = "film_identity", value = "电影identity", required = true) @PathVariable String film_identity){
        Result result = wannaService.addWanna(request,film_identity);
        if(result == null){
            return Result.error();
        }
        return Result.ok();

    }
    //用户添加想看列表
    @ApiOperation(value = "用户添加看过功能")
    @PostMapping("addDWanna/{film_identity}")
    public Result addDWanna(HttpServletRequest request, @ApiParam(name = "film_identity", value = "电影identity", required = true) @PathVariable String film_identity){
        Result result = wannaService.addDWanna(request,film_identity);
        if(result == null){
            return Result.error();
        }
        return Result.ok();

    }

}
