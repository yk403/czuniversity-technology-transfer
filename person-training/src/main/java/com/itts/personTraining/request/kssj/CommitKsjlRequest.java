package com.itts.personTraining.request.kssj;

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
@ApiModel(value = "提交考试记录")
public class CommitKsjlRequest implements Serializable {

    private static final long serialVersionUID = 8604506592518844401L;

    @ApiModelProperty(value = "考试记录ID",required = true)
    private Long id;

    @ApiModelProperty(value = "考试记录选项", required = true)
    List<CommitKsjlXxRequest> ksjlxxs;
}