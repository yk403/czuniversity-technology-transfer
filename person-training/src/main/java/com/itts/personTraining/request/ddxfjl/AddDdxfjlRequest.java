package com.itts.personTraining.request.ddxfjl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
@Data
public class AddDdxfjlRequest implements Serializable {

    private static final long serialVersionUID = 9194118486862807319L;

    /**
     * 订单名称
     */
    @ApiModelProperty(value = "订单名称", required = true)
    private String ddmc;

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
     * 优惠劵ID
     */
    @ApiModelProperty(value = "优惠劵ID")
    private Long yhjId;

    /**
     * 系统类型：technology_transaction - 技术交易；talent_training - 人才培养；user - 用户管理
     */
    @ApiModelProperty(value = "系统类型：technology_transaction - 技术交易；talent_training - 人才培养；user - 用户管理", required = true)
    private String xtlx;

    /**
     * 消费类型(数据字典项）: 例如: 人才培养系统的报名费用;人才培养系统的学习资源下载;
     */
    @ApiModelProperty(value = "消费类型(数据字典项）: 例如: 人才培养系统的报名费用;人才培养系统的学习资源下载;", required = true)
    private String xflx;

    /**
     * 消费说明: 例: 报名费用；学习资源下载
     */
    @ApiModelProperty(value = "消费说明: 例: 报名费用；学习资源下载")
    private String xfsm;

    /**
     * 支付方式: aLiPay - 支付宝；wechat - 微信；integral - 积分；balance - 余额；mixing - 混合
     */
    @ApiModelProperty(value = "支付方式: aLiPay - 支付宝；wechat - 微信；integral - 积分；balance - 余额；mixing - 混合", required = true)
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
     * 状态: 待支付;已支付;已取消;已完成;申请退款;已退款
     */
    @ApiModelProperty(value = "状态: 待支付;已支付;已取消;已完成;申请退款;已退款", required = true)
    private String zt;

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