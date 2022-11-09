package com.douban.filmservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douban.filmservice.entity.DoubanFilmLink;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-26
 */

@Repository
public interface DoubanFilmLinkMapper extends BaseMapper<DoubanFilmLink> {
    DoubanFilmLink getOne(int id);

    List<DoubanFilmLink> getAll();
}
