package com.itts.personTraining.request.xxzy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/10
 */
@Data
@ApiModel(value = "购买学习资源请求参数")
public class BuyXxzyRequest implements Serializable {

    private static final long serialVersionUID = 909874889244287821L;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", required = true)
    private Long spId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称", required = true)
    private String spmc;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式", required = true)
    private String zffs;

    /**
     * 购买总数
     */
    @ApiModelProperty(value = "购买总数", required = true)
    private Integer zsl;

    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额", required = true)
    private BigDecimal zje;

    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额", required = true)
    private BigDecimal sjzfje;

    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal yhje;

    /**
     * 支付宝支付金额(混合支付时使用)
     */
    @ApiModelProperty(value = "支付宝支付金额(混合支付时使用)")
    private BigDecimal zfbje;

    /**
     * 微信支付金额(混合支付时使用)
     */
    @ApiModelProperty(value = "微信支付金额(混合支付时使用)")
    private BigDecimal wxje;

    /**
     * 积分金额(混合支付时使用)
     */
    @ApiModelProperty(value = "积分金额(混合支付时使用)")
    private BigDecimal jfje;

    /**
     * 余额金额(混合支付时使用)
     */
    @ApiModelProperty(value = "余额金额(混合支付时使用)")
    private BigDecimal yeje;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话", required = true)
    private String lxdh;

    /**
     * 省份
     */
    @ApiModelProperty(value = "省份")
    private String sf;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String cs;

    /**
     * 区县
     */
    @ApiModelProperty(value = "区县")
    private String qx;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    private String xxdz;
}