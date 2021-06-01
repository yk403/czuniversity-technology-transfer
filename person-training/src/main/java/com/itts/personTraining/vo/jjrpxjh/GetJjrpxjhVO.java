package com.itts.personTraining.vo.jjrpxjh;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@Data
public class GetJjrpxjhVO implements Serializable {

    private static final long serialVersionUID = 2915608833127910061L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 培训计划名称
     */
    @ApiModelProperty(value = "培训计划名称")
    private String pxjhmc;

    /**
     * 学院名称
     */
    @ApiModelProperty(value = "学院名称")
    private String xymc;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID")
    private Long pcId;

    /**
     * 批次名称
     */
    @ApiModelProperty(value = "批次名称")
    private String pcMc;

    /**
     * 上课开始时间
     */
    @ApiModelProperty(value = "上课开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date skkssj;

    /**
     * 上课结束时间
     */
    @ApiModelProperty(value = "上课结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date skjssj;

    /**
     * 报名开始时间
     */
    @ApiModelProperty(value = "报名开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmkssj;

    /**
     * 报名结束时间
     */
    @ApiModelProperty(value = "报名结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmjssj;

    /**
     * 上课地点
     */
    @ApiModelProperty(value = "上课地点")
    private String dd;

    /**
     * 报名人数
     */
    @ApiModelProperty(value = "报名人数")
    private Integer bmrs;

    /**
     * 师资
     */
    @ApiModelProperty(value = "师资")
    private List<GetJjrpxjhSzVO> szs;

    /**
     * 课程
     */
    @ApiModelProperty(value = "课程")
    private List<GetJjrpxhKcVO> kcs;
}