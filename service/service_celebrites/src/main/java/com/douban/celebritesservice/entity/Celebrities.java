package com.douban.celebritesservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="Celebrities对象", description="")
public class Celebrities extends BaseEntity {

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


}
