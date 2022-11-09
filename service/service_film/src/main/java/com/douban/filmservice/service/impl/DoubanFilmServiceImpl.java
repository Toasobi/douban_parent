package com.douban.filmservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.filmservice.entity.DoubanFilm;
import com.douban.filmservice.entity.vo.FilmQuery;
import com.douban.filmservice.mapper.DoubanFilmLinkMapper;
import com.douban.filmservice.mapper.DoubanFilmMapper;
import com.douban.filmservice.service.DoubanFilmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-26
 */
@Service
public class DoubanFilmServiceImpl extends ServiceImpl<DoubanFilmMapper, DoubanFilm> implements DoubanFilmService {

    @Autowired
    private DoubanFilmServiceImpl doubanFilmService;



    @Override
    public Page<DoubanFilm> QueryWithLimit(Long current, Long limit, FilmQuery filmQuery) {
        //创建一个page对象
        Page<DoubanFilm> pageFilm = new Page<>(current,limit);
        //构建条件
        QueryWrapper<DoubanFilm> wrapper = new QueryWrapper<>();
        //多条件查询
        //判断条件只是否为空，不为空拼接条件
        String name = filmQuery.getName();
        String begin = filmQuery.getBegin();
        double score = filmQuery.getScore();

        if(!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("release_time",begin); //大于等于上映时间，gt是大于
        }

        if(!StringUtils.isEmpty(score)){
            wrapper.ge("score",score);
        }

        //调用方法实现条件分页查询
        doubanFilmService.page(pageFilm,wrapper);

        return pageFilm;
    }

    @Override
    public Page<DoubanFilm> getFilmList(long current, long limit) {
        //创建一个page对象
        Page<DoubanFilm> pageFilm = new Page<>(current,limit);
        //构建条件
        QueryWrapper<DoubanFilm> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("score");

        doubanFilmService.page(pageFilm,wrapper);

        return pageFilm;
    }
}
