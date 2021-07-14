package com.itts.personTraining.vo.yh;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Austin
 * @Data: 2021/7/13
 * @Version: 1.0.0
 * @Description:
 */
@Data
public class RpcAddYhRequest implements Serializable {

    private static final long serialVersionUID = -7068706307701678590L;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String yhm;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String mm;

    /**
     * 用户编号
     */
    @ApiModelProperty(value = "用户编号", required = true)
    private String yhbh;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名", required = true)
    private String zsxm;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", required = true)
    private String lxdh;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型", required = true)
    private String yhlx;

    /**
     * 用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员
     */
    @ApiModelProperty(value = "用户类别：研究生；经纪人；导师；企业导师；任课教师；管理员", required = true)
    private String yhlb;

    /**
     * 机构ID
     */
    @ApiModelProperty(value = "机构ID", required = true)
    private Long jgId;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", required = true)
    private String yhyx;
}
