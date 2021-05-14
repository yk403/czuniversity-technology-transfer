package com.itts.personTraining.request.xxzy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/12
 */
@Data
@ApiModel(value = "更新学习资源")
public class UpdateXxzyRequest implements Serializable {

    private static final long serialVersionUID = -5707908801740199201L;

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
}