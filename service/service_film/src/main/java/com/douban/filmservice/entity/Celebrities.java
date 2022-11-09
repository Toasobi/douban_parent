package com.douban.filmservice.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Toasobi
 * @since 2022-11-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value="Celebrities对象", description="")
public class Celebrities {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String englishName;

    private String gender;

    private String sign;

    private String birth;

    private String hometown;

    private String job;

    private String imdb;

    private String brief;

    private String avatar;

    private Integer fid;

    //当前影员属于哪部电影
    private DoubanFilm doubanFilm;

}
