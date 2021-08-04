package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.request.fjzy.AddFjzyRequest;
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
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID", required = true)
    private Long pcId;

    /**
     * 附件资源ID
     */
    @ApiModelProperty(value = "附件资源ID")
    private String fjzyId;

    /**
     * 批次号
     */
    @ApiModelProperty(value = "批次号", required = true)
    private String pch;

    /**
     * 批次名称
     */
    @ApiModelProperty(value = "批次名称")
    private String pcmc;

    /**
     * 教育类型
     */
    @ApiModelProperty(value = "教育类型", required = true)
    private String jylx;

    /**
     * 计划名称
     */
    @ApiModelProperty(value = "计划名称", required = true)
    private String jhmc;

    /**
     * 培养目标
     */
    @ApiModelProperty(value = "培养目标", required = true)
    private String pymb;

    /**
     * 学制与学习年限
     */
    @ApiModelProperty(value = "学制与学习年限", required = true)
    private String xzyxxnx;

    /**
     * 培养方式和方法
     */
    @ApiModelProperty(value = "培养方式和方法", required = true)
    private String pyfshff;

    /**
     * 培养方案与培养计划
     */
    @ApiModelProperty(value = "培养方案与培养计划", required = true)
    private String pyfaypyjh;

    /**
     * 课程设置和学分规定
     */
    @ApiModelProperty(value = "课程设置和学分规定", required = true)
    private String kcszhxfgd;

    /**
     * 实践论文
     */
    @ApiModelProperty(value = "实践论文", required = true)
    private String sjlw;

    /**
     * 培养管理
     */
    @ApiModelProperty(value = "培养管理", required = true)
    private String pygl;

    /**
     * 附件资源
     */
    @ApiModelProperty(value = "附件资源")
    private List<AddFjzyRequest> fjzys;

    /**
     * 是否下发(0:否;1:是)
     */
    private Boolean sfxf;

    /**
     * 是否删除(0:未删除;1已删除)
     */
    private Boolean sfsc;

    /**
     * 下发时间
     */
    private Date xfsj;

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
