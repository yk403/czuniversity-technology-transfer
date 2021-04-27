package com.itts.personTraining.model.spzb;

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
 * @since 2021-04-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_spzb")
@ApiModel("视频直播对象")
public class Spzb implements Serializable {

    private static final long serialVersionUID = -1904709300331219952L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 直播视频名称
     */
    @ApiModelProperty(value = "直播视频名称", required = true)
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
     * 视频存储地址
     */
    @ApiModelProperty(value = "视频存储地址")
    private String ccdz;

    /**
     * 直播视频密钥
     */
    @ApiModelProperty(value = "直播视频密钥")
    private String zbspmy;

    /**
     * 视频类型： live_broadcast - 直播；recording - 录播
     */
    @ApiModelProperty(value = "视频类型： live_broadcast - 直播；recording - 录播")
    private String splx;

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
