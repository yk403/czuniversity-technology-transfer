package com.itts.personTraining.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/7/2
 * @Version: 1.0.0
 * @Description: 能力提升综合信息
 */
@Data
@ApiModel("能力提升综合信息")
public class AbilityInfoDTO {
    /**
     * 学历
     */
    @ApiModelProperty(value = "学历")
    private String xl;

    /**
     * 专业成绩(原专业平均分)
     */
    @ApiModelProperty(value = "专业成绩(原专业平均分)")
    private Integer zycj;

    /**
     * 辅修成绩(技术转移理论平均分)
     */
    @ApiModelProperty(value = "辅修成绩(技术转移理论平均分)")
    private Integer fxcj;

    /**
     * 实践成绩(技术转移实践平均分)
     */
    @ApiModelProperty(value = "实践成绩(技术转移实践平均分)")
    private Integer sjcj;

    /**
     * 实训成绩(技术转移实训平均分)
     */
    @ApiModelProperty(value = "实训成绩(技术转移实训平均分)")
    private Integer sxcj;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID")
    private Long pcId;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号")
    private String pch;

    /**
     * 批次名称
     */
    @ApiModelProperty(value = "批次名称")
    private String pcmc;

}
