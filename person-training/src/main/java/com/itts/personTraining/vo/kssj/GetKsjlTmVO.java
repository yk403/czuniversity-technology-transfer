package com.itts.personTraining.vo.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "考试记录题目")
public class GetKsjlTmVO implements Serializable {

    private static final long serialVersionUID = -4027357389323005279L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
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
     * 题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断
     */
    @ApiModelProperty(value = "题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断", required = true)
    private String tmlx;

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
     * 考试记录题目选项
     */
    @ApiModelProperty(value = "考试记录题目选项")
    private List<GetKsjlTmXxVO> ksjlTmXxs;
}