package com.itts.personTraining.request.fjzy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/21
 */
@ApiModel(value = "新增附件资源")
@Data
public class AddFjzyRequest implements Serializable {

    private static final long serialVersionUID = -4603387495353538931L;

    /**
     * 附件资源ID
     */
    private long id;

    /**
     * 附件资源名称
     */
    @ApiModelProperty(value = "附件资源名称")
    private String fjzymc;

    /**
     * 附件资源地址
     */
    @ApiModelProperty(value = "附件资源地址", required = true)
    private String fjzydz;

    /**
     * 附件资源文件类型
     */
    @ApiModelProperty(value = "附件资源文件类型")
    private String fjzylx;

    /**
     * 附件资源大小(byte)
     */
    @ApiModelProperty(value = "附件资源大小(byte)")
    private Long fjzydx;

    /**
     * 附件资源时长（s）
     */
    @ApiModelProperty(value = "附件资源时长（s）")
    private Long fjzysc;
}