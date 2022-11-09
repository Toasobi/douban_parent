package com.douban.filmservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.commonutils.Result;
import com.douban.filmservice.entity.Celebrities;
import com.douban.filmservice.entity.DoubanFilm;
import com.douban.filmservice.entity.DoubanFilmLink;
import com.douban.filmservice.entity.vo.FilmQuery;
import com.douban.filmservice.mapper.CelebritiesMapper;
import com.douban.filmservice.mapper.DoubanFilmLinkMapper;
import com.douban.filmservice.service.DoubanFilmLinkService;
import com.douban.filmservice.service.impl.DoubanFilmServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-26
 */
@Api(description="电影管理")
@RestController
@RequestMapping("/filmservice/film")
public class DoubanFilmController extends BaseController {
    //拿到配套的service
    @Autowired
    private DoubanFilmServiceImpl doubanFilmService;
    @Autowired
    DoubanFilmLinkMapper doubanFilmLinkMapper;

    @Autowired
    CelebritiesMapper celebritiesMapper;



    //查询所有电影的方法
    @Cacheable(key = "'selectFilmList'",value = "film")
    @ApiOperation(value = "所有电影列表(带影人)")
    @GetMapping
    public Result getAllFilm(){
        List<DoubanFilmLink> list = doubanFilmLinkMapper.getAll();
        return Result.ok().data("items",list);
    }

    //指定id删除电影方法
    @ApiOperation(value = "指定id删除电影")
    @DeleteMapping("delete/{id}")
    public Result delFilm(@ApiParam(name = "id",value = "电影id", required = true) @PathVariable int id){
        boolean remove = doubanFilmService.removeById(id);
        if (remove){
            return Result.ok();
        }else {
            return Result.error();
        }

    }

    //分页查询电影的方法
    @ApiOperation(value = "分页查询电影")
    @DeleteMapping("pageFilm/{current}/{limit}")
    public Result pageListFilm(@ApiParam(name = "current",value = "当前页", required = true)@PathVariable long current,@ApiParam(name = "limit",value = "单页展示条数", required = true)
    @PathVariable long limit){

        //创建page对象
        Page<DoubanFilm> pageFilm = new Page<>(current,limit); //当前页，每页记录数
        //调用方法实现分页
        //调用方法的时候，底层封装，把所有数据封装到pageFilm中
        doubanFilmService.page(pageFilm,null);

        long total = pageFilm.getTotal(); //总记录数
        List<DoubanFilm> records = pageFilm.getRecords(); //数据list集合

        return Result.ok().data("total",total).data("rows",records);
    }

    //带分页的条件查询
    @ApiOperation(value = "带分页的条件查询")
    @PostMapping("pageFilmCondition/{current}/{limit}")
    public Result pageFilmCondition(@ApiParam(name = "current",value = "当前页", required = true)@PathVariable long current,@ApiParam(name = "limit",value = "单页展示条数", required = true)
    @PathVariable long limit, @RequestBody(required = false) FilmQuery filmQuery){

        Page<DoubanFilm> pageFilm = doubanFilmService.QueryWithLimit(current,limit,filmQuery);

        long total = pageFilm.getTotal(); //总记录数
        List<DoubanFilm> records = pageFilm.getRecords(); //数据list集合

        return Result.ok().data("total",total).data("records",records);
    }

    //添加电影方法
    @ApiOperation(value = "添加电影")
    @PostMapping("addFilm")
    public Result addFilm(@RequestBody DoubanFilm doubanFilm) {
        boolean save = doubanFilmService.save(doubanFilm);
        if (save) {
            return Result.ok();
        } else {
            return Result.error();
        }

    }


    //根据电影id查询电影
    @ApiOperation(value = "根据id查询电影(带影人)")
    @GetMapping("getFilm/{id}")
    public Result getFilmByIdentity(@PathVariable Integer id){
        DoubanFilmLink doubanFilm = doubanFilmLinkMapper.getOne(id);
        return Result.ok().data("records",doubanFilm);
    }

    //根据电影id查询电影
    @ApiOperation(value = "根据id查询电影(测试)")
    @GetMapping("getFilmById/{id}")
    public Result getFilmById(@PathVariable Integer id){
        DoubanFilmLink doubanFilm = doubanFilmLinkMapper.getOne(id);
        return Result.ok().data("records",doubanFilm);
    }


    //电影修改功能
    @ApiOperation(value = "电影修改")
    @PostMapping("upDateFilm")
    public Result updateFilm(@RequestBody DoubanFilm doubanFilm){

        boolean flag = doubanFilmService.updateById(doubanFilm);
        if (flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

    //根据影人id查询出演电影
    @ApiOperation(value = "根据影人id查询出演电影")
    @GetMapping("getCelebriteById/{id}")
    public Result getCelebriteById(@PathVariable Integer id){
        Celebrities celebrities = celebritiesMapper.getOne(id);
        return Result.ok().data("records",celebrities);
    }

    //电影排行榜
    @ApiOperation(value = "电影排行榜")
    @PostMapping("filmList/{current}/{limit}")
    public Result filmList(@ApiParam(name = "current",value = "当前页", required = true)@PathVariable long current,@ApiParam(name = "limit",value = "单页展示条数", required = true) @PathVariable long limit){
        Page<DoubanFilm> pageFilm = doubanFilmService.getFilmList(current,limit);
        long total = pageFilm.getTotal(); //总记录数
        List<DoubanFilm> records = pageFilm.getRecords(); //数据list集合
        return Result.ok().data("total",total).data("records",records);
    }

}

