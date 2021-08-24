package com.itts.personTraining.model.xsCj;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class YzyCjExcel implements Serializable {

    private static final long serialVersionUID = -7900312407875185578L;
    /**
     * 批次号
     */
    private String pch;

    /**
     * 批次名称
     */
    private String pcmc;
    /**
     * 学号
     */
    private String xh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 课程代码
     */
    private String kcdm;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 是否必修(0:否;1:是)
     */
    private Boolean sfbx;
    /**
     * 学位课(0:否;1:是)
     */
    private Boolean xwk;

    /**
     * 原专业学分
     */
    private Integer yzyxf;

    /**
     * 当前学分
     */
    private Integer dqxf;

    /**
     * 成绩
     */
    private String cj;

    /**
     * 选修学期(1-6)
     */
    private String xxxq;

    /**
     * 成绩属性:(正常,旷考,免考,作弊 重修,缓考,补考, 取消资格)
     */
    private String cjsx;

    /**
     * 补考成绩
     */
    private String bkcj;
}
