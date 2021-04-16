package com.itts.userservice.model.sjb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：数据表创建、更新实体类
 * @Author：lym
 * @Date: 2021/4/16
 */
@Data
@ApiModel("数据表对象")
public class Sjb implements Serializable {

    private static final long serialVersionUID = -2923548679349916492L;

    /**
     * 数据表名称
     */
    @ApiModelProperty(value = "数据表名称")
    private String tableName;

    /**
     * 数据表备注
     */
    @ApiModelProperty(value = "数据表备注")
    private String tableComment;
}