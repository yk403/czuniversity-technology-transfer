package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.itts.personTraining.model.yh.GetYhVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 师资综合信息对象
 */
@Data
@ApiModel("师资综合信息对象")
public class SzMsgDTO {

    /**
     * 师资id
     */
    private Long id;

    /**
     * 用户信息
     */
    private GetYhVo yhMsg;

    /**
     * 导师编号
     */
    @ApiModelProperty(value = "导师编号")
    private String dsbh;

    /**
     * 导师姓名
     */
    @ApiModelProperty(value = "导师姓名")
    private String dsxm;

    /**
     * 性别:	male - 男;	female - 女
     */
    @ApiModelProperty(value = "性别")
    private String xb;

    /**
     * 身份证号
     */
    @ApiModelProperty(value = "身份证号")
    private String sfzh;

    /**
     * 籍贯
     */
    @ApiModelProperty(value = "籍贯")
    private String jg;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private Date csrq;

    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String mz;

    /**
     * 政治面貌
     */
    @ApiModelProperty(value = "政治面貌")
    private String zzmm;

    /**
     * 文化程度
     */
    @ApiModelProperty(value = "文化程度")
    private String whcd;

    /**
     * 院系
     */
    @ApiModelProperty(value = "院系")
    private String yx;

    /**
     * 干部职务
     */
    @ApiModelProperty(value = "干部职务")
    private String gbzw;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String dh;

    /**
     * 个人照片
     */
    @ApiModelProperty(value = "个人照片")
    private String grzp;

    /**
     * 从事专业1
     */
    @ApiModelProperty(value = "从事专业1")
    private String cszyOne;

    /**
     * 从事专业2
     */
    @ApiModelProperty(value = "从事专业2")
    private String cszyTwo;

    /**
     * 从事专业3
     */
    @ApiModelProperty(value = "从事专业3")
    private String cszyThree;

    /**
     * 技术职称
     */
    @ApiModelProperty(value = "技术职称")
    private String jszc;

    /**
     * 单位电话
     */
    @ApiModelProperty(value = "单位电话")
    private String dwdh;

    /**
     * 批准硕导年月
     */
    @ApiModelProperty(value = "批准硕导年月")
    private String pzsdny;

    /**
     * 批准博导年月
     */
    @ApiModelProperty(value = "批准博导年月")
    private String pzbdny;

    /**
     * 在岗状态
     */
    @ApiModelProperty(value = "在岗状态")
    private String zgzt;

    /**
     * 导师类别:	tutor - 研究生导师;	corporate_mentor - 企业导师;	teacher - 授课教师
     */
    @ApiModelProperty(value = "导师类别")
    private String dslb;

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
