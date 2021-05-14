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
public class AddKssjRequest implements Serializable {

    private static final long serialVersionUID = 7782661226876351073L;

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
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID", required = true)
    private List<Long> tmIds;
}