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
 * @Data: 2021/5/6
 * @Version: 1.0.0
 * @Description: 考试DTO
 */
@Data
@ApiModel("考试对象")
public class KsDTO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 批次ID
     */
    @ApiModelProperty(value = "批次ID", required = true)
    private Long pcId;

    /**
     * 考试名称
     */
    @ApiModelProperty(value = "考试名称")
    private String ksmc;

    /**
     * 考试类型(0:统一考试;1:实训考试;2:证书考试;3:补考)
     */
    @ApiModelProperty(value = "试类型(0:统一考试;1:实训考试;2:证书考试;3:补考)", required = true)
    private Integer kslx;

    /**
     * 是否下发(0:否;1:是)
     */
    @ApiModelProperty(value = "是否下发(0:否;1:是)")
    private Boolean sfxf;

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

    /**
     * 课程集合
     */
    private List<KsExpDTO> ksExps;
}
