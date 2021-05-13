package com.itts.personTraining.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/5/13
 * @Version: 1.0.0
 * @Description: 考试扩展DTO
 */
@Data
@ApiModel("考试扩展对象")
public class KsExpDTO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 考试ID
     */
    @ApiModelProperty(value = "考试ID", required = true)
    private Long ksId;

    /**
     * 师资ids
     */
    @ApiModelProperty(value = "师资ids", required = true)
    private List<Long> szIds;

    /**
     * 学校教室ID
     */
    @ApiModelProperty(value = "学校教室ID", required = true)
    private Long xxjsId;

    /**
     * 教学楼名称
     */
    private String jxlmc;

    /**
     * 教室编号
     */
    private String jsbh;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 课程代码
     */
    private String kcdm;

    /**
     * 课程名称
     */
    private String kcmc;

    /**
     * 考试开始时间
     */
    @ApiModelProperty(value = "考试开始时间", required = true)
    private Date kskssj;

    /**
     * 考试结束时间
     */
    @ApiModelProperty(value = "考试结束时间", required = true)
    private Date ksjssj;

    /**
     * 是否删除(0:否;1:是)
     */
    private Boolean sfsc;

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

    /**
     * 下发时间
     */
    private Date xfsj;
}
