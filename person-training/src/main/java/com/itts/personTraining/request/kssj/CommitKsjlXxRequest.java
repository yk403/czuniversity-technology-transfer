package com.itts.personTraining.request.kssj;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/28
 */
@Data
@ApiModel(value = "提交考试记录选项")
public class CommitKsjlXxRequest implements Serializable {

    private static final long serialVersionUID = 5158310159352027040L;

    @ApiModelProperty(value = "考试记录选项ID", required = true)
    private Long id;

    @ApiModelProperty(value = "题目ID", required = true)
    private Long tmId;

    @ApiModelProperty(value = "是否选中", required = true)
    private Boolean sfxz;
}