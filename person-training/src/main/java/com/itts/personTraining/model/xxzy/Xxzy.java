package com.itts.personTraining.model.xxzy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 学习资源
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_xxzy")
@ApiModel(value = "学习资源")
public class Xxzy implements Serializable {

    private static final long serialVersionUID = 980025091203886016L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称", required = true)
    private String mc;

    /**
     * 资源编码
     */
    @ApiModelProperty(value = "资源编码", required = true)
    private String bm;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID")
    private Long kcId;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String kcmc;

    /**
     * 资源类别: in - 内部资源; out - 外部资源
     */
    @ApiModelProperty(value = "资源类别: in - 内部资源; out - 外部资源")
    private String zylb;

    /**
     * 资源一级分类
     */
    @ApiModelProperty(value = "资源一级分类")
    private String zyyjfl;

    /**
     * 资源二级分类
     */
    @ApiModelProperty(value = "资源二级分类")
    private String zyejfl;

    /**
     * 资源类型: video - 视频; textbook - 教材; courseware - 课件
     */
    @ApiModelProperty(value = "资源类型: video - 视频; textbook - 教材; courseware - 课件")
    private String zylx;

    /**
     * 资源简介
     */
    @ApiModelProperty(value = "资源简介")
    private String jj;

    /**
     * 浏览量
     */
    @ApiModelProperty(value = "浏览量")
    private Integer lll;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal jg;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String zz;

    /**
     * 视频直播ID
     */
    @ApiModelProperty(value = "视频直播ID")
    private Long spzyId;

    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址")
    private String xzdz;

    /**
     * 播放地址
     */
    @ApiModelProperty(value = "播放地址")
    private String bfdz;

    /**
     * 是否分享
     */
    @ApiModelProperty(value = "是否分享")
    private Boolean sffx;

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
