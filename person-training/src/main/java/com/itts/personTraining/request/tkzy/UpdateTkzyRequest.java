package com.itts.personTraining.request.tkzy;

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
@ApiModel(value = "更新题库资源")
public class UpdateTkzyRequest implements Serializable {

    private static final long serialVersionUID = 5178366309615416584L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID", required = true)
    private Long id;

    /**
     * 题目名称
     */
    @ApiModelProperty(value = "题目名称", required = true)
    private String mc;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称", required = true)
    private String kcmc;

    /**
     * 题目一级分类
     */
    @ApiModelProperty(value = "题目一级分类", required = true)
    private String tmyjfl;

    /**
     * 题目二级分类
     */
    @ApiModelProperty(value = "题目二级分类")
    private String tmejfl;

    /**
     * 题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断
     */
    @ApiModelProperty(value = "题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断", required = true)
    private String tmlx;

    /**
     * 题目难度(易；中；难)
     */
    @ApiModelProperty(value = "题目难度(易；中；难)", required = true)
    private String tmnd;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值", required = true)
    private Integer fz;

    /**
     * 是否正确答案（当为判断题时使用此字段）
     */
    @ApiModelProperty(value = "是否正确答案（当为判断题时使用此字段）")
    private Boolean sfzqda;

    /**
     * 出题人
     */
    @ApiModelProperty(value = "出题人")
    private String ctr;

    /**
     * 题目选项
     */
    @ApiModelProperty(value = "题目选项")
    private List<UpdateTmxxRequest> tmxxs;

    /**
     * 父级机构id
     */
    @ApiModelProperty(value = "父级机构id")
    private Long fjjgId;
}