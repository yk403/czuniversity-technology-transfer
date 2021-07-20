package com.itts.personTraining.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/7/13
 * @Version: 1.0.0
 * @Description: 经纪人报名基础信息
 */
@Data
@ApiModel("经纪人报名基础信息")
public class JjrbmInfo {

    /**
     * 批次id
     */
    @ApiModelProperty(value = "批次id", required = true)
    private Long pcId;

    /**
     * 机构id
     */
    @ApiModelProperty(value = "机构id", required = true)
    private Long jgId;

    /**
     * 姓名
     */
   @ApiModelProperty(value = "姓名", required = true)
    private String xm;

    /**
     * 性别
     */
   @ApiModelProperty(value = "性别", required = true)
    private String xb;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date csrq;

    /**
     * 身份证号
     */
   @ApiModelProperty(value = "身份证号")
    private String sfzh;

    /**
     * 籍贯
     */
   @ApiModelProperty(value = "籍贯", required = true)
    private String jg;

    /**
     * 民族
     */
   @ApiModelProperty(value = "民族", required = true)
    private String mz;

    /**
     * 政治面貌
     */
   @ApiModelProperty(value = "政治面貌", required = true)
    private String zzmm;

    /**
     * 原毕业院校
     */
   @ApiModelProperty(value = "原毕业院校", required = true)
    private String ybyyx;

    /**
     * 联系电话(手机)
     */
   @ApiModelProperty(value = "联系电话(手机)", required = true)
    private String lxdh;

}
