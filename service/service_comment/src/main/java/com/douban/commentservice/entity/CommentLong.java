package com.douban.commentservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

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
 * @since 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="CommentLong对象", description="")
public class CommentLong extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "唯一标识")
    private String identity;

    @ApiModelProperty(value = "哪部电影下")
    private String filmIdentity;

    @ApiModelProperty(value = "用户identity")
    private String userIdentity;

    @ApiModelProperty(value = "用户头像")
    private String userAvator;

    @ApiModelProperty(value = "用户名称")
    private String userNickname;

    @ApiModelProperty(value = "长评内容（1000字）")
    private String content;

    @ApiModelProperty(value = "点赞数量")
    private Integer isLike;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "是否删除 1已删除 0未删除")
    private Integer isDelete;


}
