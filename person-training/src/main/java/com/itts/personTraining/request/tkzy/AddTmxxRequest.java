package com.itts.personTraining.request.tkzy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/13
 */
@Data
@ApiModel(value = "新增题目选项")
public class AddTmxxRequest implements Serializable {

    private static final long serialVersionUID = -2528611303160730881L;

    /**
     * 选项内容
     */
    @ApiModelProperty(value = "选项内容", required = true)
    private String xxnr;

    /**
     * 是否正确答案
     */
    @ApiModelProperty(value = "是否正确答案", required = true)
    private Boolean sfzqda;
}