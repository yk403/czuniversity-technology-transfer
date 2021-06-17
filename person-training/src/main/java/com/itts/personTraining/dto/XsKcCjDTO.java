package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Austin
 * @Data: 2021/6/2
 * @Version: 1.0.0
 * @Description: 学生成绩扩展对象
 */
@Data
@ApiModel("学生成绩扩展对象")
public class XsKcCjDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 学生ID
     */
    @ApiModelProperty(value = "学生ID")
    private Long xsId;

    /**
     * 学生成绩ID
     */
    @ApiModelProperty(value = "学生成绩ID")
    private Long xsCjId;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID")
    private Long kcId;

    /**
     * 课程类型(1:原专业课程;2:技术转移专业课程)
     */
    @ApiModelProperty(value = "课程类型(1:原专业课程;2:技术转移专业课程)")
    private Integer kclx;

    /**
     * 知识模块ID
     */
    @ApiModelProperty(value = "知识模块ID")
    private Long zsmkId;

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
     * 原专业学分
     */
    @ApiModelProperty(value = "原专业学分")
    private Integer yzyxf;

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
     * 是否删除(0:否;1:是)
     */
    private Boolean sfsc;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 创建时间
     */
    @TableField(value = "cjsj", fill = FieldFill.INSERT) // 新增执行
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    @TableField(value = "gxsj", fill = FieldFill.INSERT_UPDATE) // 新增和更新执行
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;
}
