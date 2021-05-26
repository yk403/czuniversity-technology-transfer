package com.itts.personTraining.model.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 考试试卷
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_kssj")
@ApiModel(value = "考试试卷")
public class Kssj implements Serializable {

    private static final long serialVersionUID = 813719735615630351L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String kcMc;

    /**
     * 试卷名称
     */
    @ApiModelProperty(value = "试卷名称", required = true)
    private String sjmc;

    /**
     * 题目总数
     */
    @ApiModelProperty(value = "题目总数")
    private Integer tmzs;

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
     * 是否上架
     */
    @ApiModelProperty(value = "是否上架")
    private Boolean sfsj;

    /**
     * 上架时间
     */
    @ApiModelProperty(value = "上架时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sjsj;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long gxr;
}
