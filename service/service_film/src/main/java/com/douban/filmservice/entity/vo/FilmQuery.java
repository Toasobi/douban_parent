package com.douban.filmservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//前端传过来的条件封装在这里进行 vo包
//电影查询类
@Data
public class FilmQuery {
    @ApiModelProperty(value = "电影名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "查询上映时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "评分")
    private double score;

}
