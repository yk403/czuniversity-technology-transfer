package com.itts.personTraining.vo.kssj;

import com.itts.personTraining.vo.sjpz.SjpzVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/28
 */
@Data
@ApiModel(value = "考试记录")
public class GetKsjlVO implements Serializable {

    private static final long serialVersionUID = 4536786023074924279L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 试卷ID
     */
    @ApiModelProperty(value = "试卷ID")
    private Long sjId;

    /**
     * 试卷总分
     */
    @ApiModelProperty(value = "试卷总分")
    private Integer sjzf;

    /**
     * 题目总数
     */
    @ApiModelProperty(value = "题目总数")
    private Integer tmzs;

    /**
     * 最终成绩
     */
    @ApiModelProperty(value = "最终成绩")
    private Integer zzcj;

    /**
     * 试卷类型
     */
    @ApiModelProperty(value = "试卷类型：single_subject - 单科；comprehensive - 综合")
    private String sjlx;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private long cjsj;

    /**
     * 考试记录题目
     */
    @ApiModelProperty(value = "考试记录题目")
    private List<GetKsjlTmVO> ksjlTms;

    @ApiModelProperty(value = "试卷配置")
    private SjpzVO sjpzVO;
}