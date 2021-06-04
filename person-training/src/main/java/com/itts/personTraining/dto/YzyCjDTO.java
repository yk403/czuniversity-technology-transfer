package com.itts.personTraining.dto;

import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Austin
 * @Data: 2021/6/4
 * @Version: 1.0.0
 * @Description: 原专业成绩导入实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ContentRowHeight(12)
@HeadRowHeight(12)
@ColumnWidth(20)
public class YzyCjDTO implements Serializable {

    private static final long serialVersionUID = -6567576480726951805L;

    /**
     * 课程代码
     */
    @ApiModelProperty(value = "课程代码")
    private String kcdm;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String kcmc;

    /**
     * 课程学时(如总学时为36学时)
     */
    @ApiModelProperty(value = "课程学时(如总学时为36学时)")
    private String kcxs;

    /**
     * 课程学分
     */
    @ApiModelProperty(value = "课程学分")
    private Integer kcxf;

    /**
     * 是否必修(0:否;1:是)
     */
    @ApiModelProperty(value = "是否必修(0:否;1:是)")
    private Boolean sfbx;

    /**
     * 学位课(0:否;1:是)
     */
    @ApiModelProperty(value = "学位课(0:否;1:是)")
    private Boolean xwk;

    /**
     * 当前学分
     */
    @ApiModelProperty(value = "当前学分")
    private Integer dqxf;

    /**
     * 成绩
     */
    @ApiModelProperty(value = "成绩")
    private String cj;

    /**
     * 选修学期(1-6)
     */
    @ApiModelProperty(value = "选修学期(1-6)")
    private String xxxq;

    /**
     * 成绩属性:(正常,旷考,免考,作弊 重修,缓考,补考, 取消资格)
     */
    @ApiModelProperty(value = "成绩属性:(正常,旷考,免考,作弊 重修,缓考,补考, 取消资格)")
    private String cjsx;

    /**
     * 补考成绩
     */
    @ApiModelProperty(value = "补考成绩")
    private String bkcj;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
}
