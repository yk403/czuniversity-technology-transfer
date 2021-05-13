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
 * @Data: 2021/5/11
 * @Version: 1.0.0
 * @Description: 培养计划DTO
 */
@Data
@ApiModel("培养计划对象")
public class PyJhDTO {
    /**
     * 培养计划主键
     */
    private Long id;

    /**
     * 课程ids
     */
    private List<Long> kcIds;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID", required = true)
    private Long pcId;

    /**
     * 是否下发(0:否;1:是)
     */
    @ApiModelProperty(value = "是否下发(0:否;1:是)", required = true)
    private Boolean sfxf;

    /**
     * 培养方案
     */
    @ApiModelProperty(value = "培养方案", required = true)
    private String pyfa;

    /**
     * 培养计划
     */
    @ApiModelProperty(value = "培养计划", required = true)
    private String pyjh;

    /**
     * 教学大纲
     */
    @ApiModelProperty(value = "教学大纲", required = true)
    private String jxdg;

    /**
     * 学生类别名称
     */
    @ApiModelProperty(value = "学生类别名称", required = true)
    private String xslbmc;

    /**
     * 上传时间
     */
    private Date scsj;

    /**
     * 下发时间
     */
    private Date xfsj;

    /**
     * 是否删除(0:否;1:是)
     */
    private boolean sfsc;

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
