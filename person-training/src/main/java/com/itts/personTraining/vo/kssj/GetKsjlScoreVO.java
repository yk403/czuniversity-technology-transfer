package com.itts.personTraining.vo.kssj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/22
 */
@Data
@ApiModel(value = "获取考试记录分数")
public class GetKsjlScoreVO implements Serializable {

    private static final long serialVersionUID = -3060024410800525200L;

    /**
     * 试卷总分
     */
    @ApiModelProperty(value = "试卷总分")
    private Integer sjzf;

    /**
     * 最终得分总分
     */
    @ApiModelProperty(value = "最终得分总分")
    private Integer zzcj;

    /**
     * 判断总分
     */
    @ApiModelProperty(value = "判断总分")
    private Integer pdzf;

    /**
     * 判断题最终得分
     */
    @ApiModelProperty(value = "判断题最终得分")
    private Integer pddf;

    /**
     * 单选题总分
     */
    @ApiModelProperty(value = "单选题总分")
    private Integer danxzf;

    /**
     * 单选最终得分
     */
    @ApiModelProperty(value = "单选最终得分")
    private Integer danxdf;

    /**
     * 多选总分
     */
    @ApiModelProperty(value = "多选总分")
    private Integer duoxzf;

    /**
     * 多选最终得分
     */
    @ApiModelProperty(value = "多选最终得分")
    private Integer duoxdf;
}