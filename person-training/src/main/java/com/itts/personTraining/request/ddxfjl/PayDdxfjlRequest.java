package com.itts.personTraining.request.ddxfjl;

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
public class PayDdxfjlRequest implements Serializable {

    private static final long serialVersionUID = 4719784370173949103L;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String bh;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Long spId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String spmc;

    /**
     * 支付方式
     */
    @ApiModelProperty(value = "支付方式")
    private String zffs;

    /**
     * 购买数量
     */
    @ApiModelProperty(value = "购买数量")
    private Integer zsl;

    /**
     * 总金额
     */
    @ApiModelProperty(value = "总金额")
    private BigDecimal zje;

    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额")
    private BigDecimal sjzfje;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lxdh;
}