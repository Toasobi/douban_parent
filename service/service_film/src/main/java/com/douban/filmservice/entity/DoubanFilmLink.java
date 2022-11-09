package com.douban.filmservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.douban.filmservice.entity.BaseEntity;
import com.douban.filmservice.entity.Celebrities;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 
 * </p>
 *
 * @author Toasobi
 * @since 2022-10-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="DoubanFilm对象", description="")
public class DoubanFilmLink extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //指定identity生成规则，auto是自动生成的意思
    @TableId(value = "identity",type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "唯一标识")
    private String identity;

    @ApiModelProperty(value = "电影的名字")
    private String name;

    @ApiModelProperty(value = "剧照")
    private String img;

    @ApiModelProperty(value = "评分")
    private Double score;

    @ApiModelProperty(value = "电影简介")
    private String synopsis;

    @ApiModelProperty(value = "评分人数")
    private Integer counting;

    @TableLogic  //逻辑删除
    @ApiModelProperty(value = "逻辑删除 1已删除 0未删除")
    private Integer isDelete;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "删除时间")
    private LocalDateTime deletedAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ApiModelProperty(value = "上映时间")
    private LocalDateTime releaseTime;


    @TableField(exist = false)
    //很多个演员
    private List<Celebrities> celebritiesList;




}
