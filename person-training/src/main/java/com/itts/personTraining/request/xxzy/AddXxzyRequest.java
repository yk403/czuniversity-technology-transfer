package com.itts.personTraining.request.xxzy;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itts.personTraining.request.fjzy.AddFjzyRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/12
 */
@Data
@ApiModel(value = "添加学习资源")
public class AddXxzyRequest implements Serializable {

    private static final long serialVersionUID = 5884577264377845259L;

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
    @ApiModelProperty(value = "课程名称", required = true)
    private String kcmc;

    /**
     * 资源类别: in - 内部资源; out - 外部资源
     */
    @ApiModelProperty(value = "资源类别: in - 内部资源; out - 外部资源", required = true)
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
     * 展示图片
     */
    @ApiModelProperty(value = "展示图片")
    private String zstp;

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
     * 是否分享
     */
    @ApiModelProperty(value = "是否分享")
    private Boolean sffx;

    /**
     * 是否公开
     */
    @ApiModelProperty(value = "是否公开")
    private Boolean sfgk;

    /**
     * 公开开始时间
     */
    @ApiModelProperty(value = "公开开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gkkssj;

    /**
     * 公开结束时间
     */
    @ApiModelProperty(value = "公开结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gkjssj;

    /**
     * 附件资源
     */
    @ApiModelProperty(value = "附件资源")
    private List<AddFjzyRequest> fjzys;
}