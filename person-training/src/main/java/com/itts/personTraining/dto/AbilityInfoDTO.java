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
    //学历

    /**
     * 主修专业总学分(获得)(专业成绩)
     */
    @ApiModelProperty(value = "主修专业总学分(获得)")
    private Integer zxzyzxf;

    /**
     * 总学分(技术转移获得)(辅修成绩)
     */
    @ApiModelProperty(value = "总学分(技术转移获得)")
    private Integer zxf;

    /**
     * 实践成绩
     */
    @ApiModelProperty(value = "实践成绩")
    private String sjcj;

    /**
     * 实训成绩
     */
    @ApiModelProperty(value = "实训成绩")
    private String sxcj;

    /**
     * 实践列表
     */
    @ApiModelProperty(value = "实践列表")
    private List<SjDTO> sjDTOList;

    /**
     * 课程列表
     */
    @ApiModelProperty(value = "课程列表")
    private List<KcDTO> kcDTOList;

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
