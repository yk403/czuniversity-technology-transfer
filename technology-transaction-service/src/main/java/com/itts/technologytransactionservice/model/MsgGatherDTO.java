package com.itts.technologytransactionservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Austin
 * @Data: 2021/4/1
 * @Version: 1.0.0
 * @Description: 信息采集DTO
 */
@Data
public class MsgGatherDTO {
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "类型(1技术成果,2技术需求)")
    private Integer lx;

    @ApiModelProperty(value = "名称")
    private String mc;

    @ApiModelProperty(value = "合作价格")
    private BigDecimal hzjg;

    @ApiModelProperty(value = "单位名称")
    private String dwmc;

    @ApiModelProperty(value = "地址")
    private String dz;

    @ApiModelProperty(value = "发布审核状态(0待提交;1待审核;2通过;3整改;4拒绝)")
    private Integer fbshzt;

    @ApiModelProperty(value = "创建时间")
    private Data cjsj;

}
