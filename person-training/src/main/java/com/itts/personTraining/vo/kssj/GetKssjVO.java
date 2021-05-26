package com.itts.personTraining.vo.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.itts.personTraining.vo.tkzy.GetTkzyVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/14
 */
@Data
public class GetKssjVO implements Serializable {

    private static final long serialVersionUID = -3362107184980600735L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

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
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 试卷题目
     */
    @ApiModelProperty(value = "试卷题目")
    private Map<String, List<GetTkzyVO>> tms;
}