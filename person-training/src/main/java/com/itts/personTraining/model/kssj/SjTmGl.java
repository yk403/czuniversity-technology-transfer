package com.itts.personTraining.model.kssj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 试卷题目关联
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_sj_tm_gl")
@ApiModel(value = "试卷题目关联")
public class SjTmGl implements Serializable {

    private static final long serialVersionUID = -4452591704203081298L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 题目ID
     */
    @ApiModelProperty(value = "题目ID", required = true)
    private Long tmId;

    /**
     * 考试试卷ID
     */
    @ApiModelProperty(value = "考试试卷ID", required = true)
    private Long kssjId;


}
