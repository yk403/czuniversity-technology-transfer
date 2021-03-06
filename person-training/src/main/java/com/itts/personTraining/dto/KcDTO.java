package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/4/23
 * @Version: 1.0.0
 * @Description: 课程DTO
 */
@Data
@ApiModel("课程对象")
public class KcDTO {
    /**
     * 课程主键id
     */
    private Long id;

    /**
     * 转化给前端使用
     */
    private Long kcId;

    /**
     * 父级机构ID
     */
    private Long fjjgId;

    /**
     * 师资ids
     */
   /* @ApiModelProperty(value = "师资ids", required = true)
    private List<Long> szIds;*/

    /**
     * 知识模块ID(1:公共知识模块;2:政策法规模块;3:实务技能模块一;4:实务技能模块二;5:实务技能模块三;6:能力提升模块)
     */
    @ApiModelProperty(value = "知识模块ID", required = true)
    private Long zsmkId;

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
     * 课程类型(theory_class：理论课；practical_training：实训课；practice_course：实践课)
     */
    @ApiModelProperty(value = "课程类型", required = true)
    private String kclx;

    /**
     * 课程图片
     */
    private String kctp;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称", required = true)
    private String kcmc;

    /**
     * 课程代码
     */
    @ApiModelProperty(value = "课程代码", required = true)
    private String kcdm;

    /**
     * 课程学时(如总学时为36学时)
     */
    @ApiModelProperty(value = "课程学时", required = true)
    private String kcxs;

    /**
     * 课程学分
     */
    @ApiModelProperty(value = "课程学分", required = true)
    private Integer kcxf;

    /**
     * 开课时间
     */
    @ApiModelProperty(value = "开课时间", required = true)
    private String kkxq;

    /**
     * 开课学院
     */
    @ApiModelProperty(value = "开课学院", required = true)
    private String kkxy;

    /**
     * 授课方式:	线上授课；线下授课
     */
    @ApiModelProperty(value = "授课方式: 线上授课；线下授课", required = true)
    private String skfs;

    /**
     * 教学讲义
     */
    @ApiModelProperty(value = "教学讲义")
    private String jxjy;

    /**
     * 教材附件
     */
    @ApiModelProperty(value = "教材附件")
    private String jcfj;

    /**
     * 课件
     */
    @ApiModelProperty(value = "课件")
    private String kj;

    /**
     * 讲案
     */
    @ApiModelProperty(value = "讲案")
    private String ja;

    /**
     * 课件视频
     */
    @ApiModelProperty(value = "课件视频")
    private String kjsp;

    /**
     * 视频
     */
    @ApiModelProperty(value = "视频")
    private String sp;

    /**
     * 是否必修(0:否;1:是)
     */
    @ApiModelProperty(value = "是否必修")
    private Boolean sfbx;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 是否删除(0:否；1:是)
     */
    private boolean sfsc;

    /**
     * 是否下发(0:否;1:是)
     */
    @ApiModelProperty(value = "是否下发")
    private Boolean sfxf;

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
