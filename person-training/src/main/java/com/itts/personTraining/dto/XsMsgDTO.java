package com.itts.personTraining.dto;

import com.itts.personTraining.model.yh.GetYhVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 学生综合信息对象
 */
@Data
@ApiModel("学生综合信息对象")
public class XsMsgDTO {

    /**
     * 学生ID
     */
    private Long id;

    /**
     * 考试试卷id
     */
    private Long kssjId;

    /**
     * 用户信息
     */
    private GetYhVo yhMsg;

    /**
     * 院系
     */
    @ApiModelProperty(value = "院系")
    private String yx;

    /**
     * 学号
     */
    @ApiModelProperty(value = "学号", required = true)
    private String xh;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String xm;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 学员类型
     */
    @ApiModelProperty(value = "学员类型", required = true)
    private List<String> xylx;

    /**
     * 联系电话(手机)
     */
    @ApiModelProperty(value = "联系电话(手机)", required = true)
    private String lxdh;

    /**
     * 原毕业院校
     */
    @ApiModelProperty(value = "原毕业院校")
    private String ybyyx;

    /**
     * 原专业代码
     */
    @ApiModelProperty(value = "原专业代码", required = true)
    private String yzydm;

    /**
     * 原专业
     */
    @ApiModelProperty(value = "原专业", required = true)
    private String yzy;

    /**
     * 报名方式
     */
    @ApiModelProperty(value = "报名方式")
    private String bmfs;

    /**
     * 考试通知
     */
    @ApiModelProperty(value = "考试通知")
    private Long kstz;

    /**
     * 成绩通知
     */
    @ApiModelProperty(value = "成绩通知")
    private Long cjtz;

    /**
     * 实践通知
     */
    @ApiModelProperty(value = "实践通知")
    private Long sjtz;

    /**
     * 消费通知
     */
    @ApiModelProperty(value = "消费通知")
    private Long xftz;

    /**
     * 其他通知
     */
    @ApiModelProperty(value = "其他通知")
    private Long qttz;

}
