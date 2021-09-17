package com.itts.technologytransactionservice.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class JsMsgDTO extends JsMsg implements Serializable {
    private static final long serialVersionUID = 4060404854692521252L;

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
     * 用户类别：研究生；经纪人；导师；企业导师；任课教师；内部专家；外部专家；管理员
     */
    @ApiModelProperty(value = "用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员")
    private String yhlb;

}
