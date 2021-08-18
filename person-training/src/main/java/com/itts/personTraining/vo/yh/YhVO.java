package com.itts.personTraining.vo.yh;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户")
public class YhVO implements Serializable {
    private static final long serialVersionUID = 7961257508184812980L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String yhm;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号")
    private String yhbh;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String zsxm;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lxdh;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String yhtx;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private String yhlx;

    /**
     * 用户类别
     */
    @ApiModelProperty(value = "用户类别：研究生；经纪人；导师；企业导师；任课教师；领导；内部专家")
    private String yhlb;

    /**
     * 院系
     */
    private String yx;
    /**
     * 研究生原专业
     */
    private String yzy;

    /**
     * 经纪人原毕业院校
     */
    private String ybyyx;
    /**
     * 师资技术职称
     */
    private String jszc;
    /**
     * 专家专业技术职位
     */
    @ApiModelProperty(value = "专业技术职位")
    private String zyjszw;
    /**
     * 专家研究领域
     */
    @ApiModelProperty(value = "研究领域")
    private String yjly;
}
