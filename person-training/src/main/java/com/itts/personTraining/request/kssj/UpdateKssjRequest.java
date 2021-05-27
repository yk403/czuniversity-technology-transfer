package com.itts.personTraining.request.kssj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/14
 */
@Data
public class UpdateKssjRequest implements Serializable {

    private static final long serialVersionUID = -6972819231045866940L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", required = true)
    private Long id;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 试卷名称
     */
    @ApiModelProperty(value = "试卷名称", required = true)
    private String sjmc;

    /**
     * 试卷总分
     */
    @ApiModelProperty(value = "试卷总分")
    private Integer sjzf;

    /**
     * 判断题总分
     */
    @ApiModelProperty(value = "判断题总分")
    private Integer pdzf;

    /**
     * 单选题总分
     */
    @ApiModelProperty(value = "单选题总分")
    private Integer danzf;

    /**
     * 多选题总分
     */
    @ApiModelProperty(value = "单选题总分")
    private Integer duozf;

    /**
     * 考试类型：single_subject - 单科；comprehensive - 综合
     */
    @ApiModelProperty(value = "考试类型：single_subject - 单科；comprehensive - 综合", required = true)
    private String sjlx;

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID", required = true)
    private List<Long> tmIds;
}