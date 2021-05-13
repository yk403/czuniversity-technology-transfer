package com.itts.personTraining.model.tkzy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 题库资源
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_tkzy")
@ApiModel(value = "题库资源")
public class Tkzy implements Serializable {

    private static final long serialVersionUID = 6606531222388771437L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 题目名称
     */
    @ApiModelProperty(value = "题目名称", required = true)
    private String mc;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID", required = true)
    private Long kcId;

    /**
     * 题目一级分类
     */
    @ApiModelProperty(value = "题目一级分类", required = true)
    private String tmyjfl;

    /**
     * 题目二级分类
     */
    @ApiModelProperty(value = "题目二级分类", required = true)
    private String tmejfl;

    /**
     * 题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断
     */
    @ApiModelProperty(value = "题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断", required = true)
    private String tmlx;

    /**
     * 分值
     */
    @ApiModelProperty(value = "分值", required = true)
    private Integer fz;

    /**
     * 是否正确答案（当为判断题时使用此字段）
     */
    @ApiModelProperty(value = "是否正确答案（当为判断题时使用此字段）")
    private Boolean sfzqda;

    /**
     * 出题人
     */
    @ApiModelProperty(value = "出题人")
    private String ctr;

    /**
     * 是否上架
     */
    @ApiModelProperty(value = "是否上架")
    private Boolean sfsj;

    /**
     * 上架时间
     */
    @ApiModelProperty(value = "上架时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sjsj;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除")
    private Boolean sfsc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long cjr;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gxsj;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long gxr;
}
