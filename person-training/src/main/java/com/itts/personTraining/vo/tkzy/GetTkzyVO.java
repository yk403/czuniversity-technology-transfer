package com.itts.personTraining.vo.tkzy;

import com.itts.personTraining.model.tkzy.Tkzy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/13
 */
@Data
@ApiModel(value = "获取题库资源详情")
public class GetTkzyVO extends Tkzy implements Serializable {

    private static final long serialVersionUID = -6131597549852681604L;

    /**
     * 题目选项
     */
    @ApiModelProperty(value = "题目选项")
    private List<GetTmxxVO> tmxxs;
}