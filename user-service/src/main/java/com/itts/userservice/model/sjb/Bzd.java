package com.itts.userservice.model.sjb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/16
 */
@Data
@ApiModel("数据表字段对象")
public class Bzd implements Serializable {

    private static final long serialVersionUID = 7315936091697898646L;

    /**
     * 字段名称
     */
    @ApiModelProperty(value = "字段名称")
    private String columnName;

    /**
     * 字段备注
     */
    @ApiModelProperty(value = "字段备注")
    private String columnComment;

    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型")
    private String dataType;

    /**
     * 字段长度
     */
    @ApiModelProperty(value = "字段长度")
    private Integer columnLength;

    /**
     * 字段类型及字段长度
     */
    @ApiModelProperty(value = "字段类型及字段长度")
    private String columnType;

    /**
     * 字段键值：主键、唯一索引、外键
     */
    @ApiModelProperty(value = "字段键值：主键、唯一索引、外键")
    private String columnKey;

    /**
    *主键是否自增: auto_increment - 自增
    */
    @ApiModelProperty("主键是否自增: auto_increment - 自增")
    private String extra;

    /**
     * 是否允许为空
     */
    @ApiModelProperty(value = "是否允许为空：YES - 是，NO - 否")
    private String nullValueFlag;

    /**
     * 默认值
     */
    @ApiModelProperty(value = "默认值")
    private String columnDefault;
}