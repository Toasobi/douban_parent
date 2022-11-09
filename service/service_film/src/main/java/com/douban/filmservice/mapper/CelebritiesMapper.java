package com.douban.filmservice.mapper;

import com.douban.filmservice.entity.Celebrities;
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
public interface CelebritiesMapper {
    Celebrities getOne(int id);

    List<Celebrities>getAll();

}
