package com.itts.technologytransactionservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
 *@Auther: yukai
 *@Date: 2021/05/28/14:38
 *@Desription:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LyLt对象", description="")
@TableName("t_ly_lt")
public class LyLt extends Model<LyLt> {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "聊天室id")
    private Integer roomId;

    @ApiModelProperty(value = "发送人id")
    private Integer sendId;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

    @ApiModelProperty(value = "内容")
    private String content;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
