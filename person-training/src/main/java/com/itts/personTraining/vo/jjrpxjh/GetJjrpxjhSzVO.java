package com.itts.personTraining.vo.jjrpxjh;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@Data
public class GetJjrpxjhSzVO implements Serializable {

    private static final long serialVersionUID = -7657888303786996587L;

    /**
     * 学院名称
     */
    @ApiModelProperty(value = "学院名称")
    private String xyMc;

    /**
     * 导师姓名
     */
    @ApiModelProperty(value = "导师姓名")
    private String dsxm;

    /**
     * 性别:	male - 男;	female - 女
     */
    @ApiModelProperty(value = "性别:male - 男;female - 女")
    private String xb;

    /**
     * 文化程度
     */
    @ApiModelProperty(value = "文化程度")
    private String whcd;

    /**
     * 个人照片
     */
    @ApiModelProperty(value = "个人照片")
    private String grzp;

    /**
     * 行业领域
     */
    @ApiModelProperty(value = "行业领域")
    private String hyly;

    /**
     * 驻入时间
     */
    @ApiModelProperty(value = "行业领域")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zrsj;
}