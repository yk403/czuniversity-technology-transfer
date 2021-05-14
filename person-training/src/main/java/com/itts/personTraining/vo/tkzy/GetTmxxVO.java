package com.itts.personTraining.vo.tkzy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "获取题目选项")
public class GetTmxxVO implements Serializable {

    private static final long serialVersionUID = 1943460828760434819L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID", required = true)
    private Long tmId;

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