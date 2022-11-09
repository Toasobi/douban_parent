package com.douban.commentservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="CommentShort对象", description="")
public class CommentShort extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "唯一标识")
    private String identity;

    @ApiModelProperty(value = "哪部电影下")
    private String filmIdentity;

    @ApiModelProperty(value = "所属长评identity,无则默认0，有则parent_id设为1")
    private String longIdentity;

    @ApiModelProperty(value = "评论分级 0：1级 1:2级")
    private Integer parentId;

    @ApiModelProperty(value = "用户identity")
    private String userIdentity;

    @ApiModelProperty(value = "用户头像")
    private String userAvator;

    @ApiModelProperty(value = "是否@人，无则默认null")
    @TableField("user_Tonickname")
    private String userTonickname;

    @ApiModelProperty(value = "用户名称")
    private String userNickname;

    @ApiModelProperty(value = "短评内容（100字）")
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
