package com.itts.personTraining.request.jjrpxjh;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@Data
@ApiModel(value = "增加经纪人培训计划")
public class AddJjrpxjhRequest implements Serializable {

    private static final long serialVersionUID = -3814534119070669181L;

    /**
     * 培训计划名称
     */
    @ApiModelProperty(value = "培训计划名称", required = true)
    private String pxjhmc;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID")
    private Long jgId;

    /**
     * 学院名称
     */
    @ApiModelProperty(value = "学院名称", required = true)
    private String xymc;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID", required = true)
    private Long pcId;

    /**
     * 批次名称
     */
    @ApiModelProperty(value = "批次名称", required = true)
    private String pcMc;

    /**
     * 教育类型
     */
    @ApiModelProperty(value = "教育类型", required = true)
    private String jylx;

    /**
     * 学员类型
     */
    @ApiModelProperty(value = "学员类型", required = true)
    private String xylx;

    /**
     * 上课开始时间
     */
    @ApiModelProperty(value = "上课开始时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date skkssj;

    /**
     * 上课结束时间
     */
    @ApiModelProperty(value = "上课结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date skjssj;

    /**
     * 报名开始时间
     */
    @ApiModelProperty(value = "报名开始时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmkssj;

    /**
     * 报名结束时间
     */
    @ApiModelProperty(value = "报名结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bmjssj;

    /**
     * 上课地点
     */
    @ApiModelProperty(value = "上课地点", required = true)
    private String dd;
}