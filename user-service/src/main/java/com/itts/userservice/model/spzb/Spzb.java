package com.itts.userservice.model.spzb;

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
 * 视频直播
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_spzb")
@ApiModel(value = "视频直播")
public class Spzb implements Serializable {

    private static final long serialVersionUID = -1665000294509830270L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程ID
     */
    @ApiModelProperty(value = "课程ID")
    private Long kcId;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String kcMc;

    /**
     * 直播视频名称
     */
    @ApiModelProperty(value = "直播视频名称")
    private String zbspmc;

    /**
     * 直播视频介绍
     */
    @ApiModelProperty(value = "直播视频介绍")
    private String zbspjs;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date kssj;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date jssj;

    /**
     * 主播名称
     */
    @ApiModelProperty(value = "主播名称")
    private String zbmc;

    /**
     * 所属系统
     */
    @ApiModelProperty(value = "所属系统")
    private String ssxt;

    /**
     * 视频存储地址
     */
    @ApiModelProperty(value = "视频存储地址")
    private String ccdz;

    /**
     * 直播视频密钥
     */
    @ApiModelProperty(value = "直播视频密钥", required = true)
    private String zbspmy;

    /**
     * 视频类型： live_broadcast - 直播；recording - 录播
     */
    @ApiModelProperty(value = "视频类型： live_broadcast - 直播；recording - 录播")
    private String splx;

    /**
     * 录播项目ID
     */
    @ApiModelProperty(value = "录播项目ID")
    private String xmId;

    /**
     * 直播推流域名
     */
    @ApiModelProperty(value = "直播推流域名")
    private String tlym;

    /**
     * 录制流名
     */
    @ApiModelProperty(value = "录制流名")
    private String lzlm;

    /**
     * 录制格式
     */
    @ApiModelProperty(value = "录制格式")
    private String lzgs;

    /**
     * 媒资ID
     */
    @ApiModelProperty(value = "媒资ID")
    private String mzId;

    /**
     * 视频播放地址
     */
    @ApiModelProperty(value = "视频播放地址")
    private String bfdz;

    /**
     * 文件大小（byte）
     */
    @ApiModelProperty(value = "文件大小（byte）")
    private Long wxdx;

    /**
     * 视频时长（s）
     */
    @ApiModelProperty(value = "视频时长（s）")
    private Long spsc;

    /**
     * 视频录制开始时间
     */
    @ApiModelProperty(value = "视频录制开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lzkssj;

    /**
     * 视频录制结束时间
     */
    @ApiModelProperty(value = "视频录制结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lzjssj;

    /**
     * 视频录制文件分辨率宽度
     */
    @ApiModelProperty(value = "视频录制文件分辨率宽度")
    private Integer spkd;

    /**
     * 视频录制文件分辨率高度
     */
    @ApiModelProperty(value = "视频录制文件分辨率高度")
    private Integer spgd;

    /**
     * 存放obs桶所在区域
     */
    @ApiModelProperty(value = "存放obs桶所在区域")
    private String obsqy;

    /**
     * 存放obs所在的桶
     */
    @ApiModelProperty(value = "存放obs所在的桶")
    private String obst;

    /**
     * 存放obs所在的路径
     */
    @ApiModelProperty(value = "存放obs所在的路径")
    private String obslj;

    /**
     * 录制描述信息
     */
    @ApiModelProperty(value = "录制描述信息")
    private String msxx;

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
