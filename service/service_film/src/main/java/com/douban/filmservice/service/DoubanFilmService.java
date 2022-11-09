package com.douban.filmservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.douban.filmservice.entity.DoubanFilm;
import com.baomidou.mybatisplus.extension.service.IService;
import com.douban.filmservice.entity.vo.FilmQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-26
 */

public interface DoubanFilmService extends IService<DoubanFilm> {

    Page<DoubanFilm> QueryWithLimit(Long current, Long limit, FilmQuery filmQuery);

    Page<DoubanFilm> getFilmList(long current, long limit);
}
