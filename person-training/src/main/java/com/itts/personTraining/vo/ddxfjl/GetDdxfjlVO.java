package com.itts.personTraining.vo.ddxfjl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@Data
public class GetDdxfjlVO implements Serializable {

    private static final long serialVersionUID = 4415799612594196369L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String bh;

    /**
     * 订单名称
     */
    private String ddmc;

    /**
     * 商品ID
     */
    private Long spId;

    /**
     * 商品名称
     */
    private String spmc;

    /**
     * 优惠劵ID
     */
    private Long yhjId;

    /**
     * 系统类型：technology_transaction - 技术交易；talent_training - 人才培养；user - 用户管理
     */
    private String xtlx;

    /**
     * 消费类型(数据字典项）: 例如: 人才培养系统的报名费用;人才培养系统的学习资源下载;
     */
    private String xflx;

    /**
     * 消费说明: 例: 报名费用；学习资源下载
     */
    private String xfsm;

    /**
     * 支付方式: aLiPay - 支付宝；wechat - 微信；integral - 积分；balance - 余额；mixing - 混合
     */
    private String zffs;

    /**
     * 购买总数
     */
    private Integer zsl;

    /**
     * 总金额
     */
    private BigDecimal zje;

    /**
     * 实际支付金额
     */
    private BigDecimal sjzfje;

    /**
     * 优惠金额
     */
    private BigDecimal yhje;

    /**
     * 支付宝支付金额(混合支付时使用)
     */
    private BigDecimal zfbje;

    /**
     * 微信支付金额(混合支付时使用)
     */
    private BigDecimal wxje;

    /**
     * 积分金额(混合支付时使用)
     */
    private BigDecimal jfje;

    /**
     * 余额金额(混合支付时使用)
     */
    private BigDecimal yeje;

    /**
     * 状态: 待支付;已支付;已取消;已完成;申请退款;已退款
     */
    private String zt;

    /**
     * 联系电话
     */
    private String lxdh;

    /**
     * 省份
     */
    private String sf;

    /**
     * 城市
     */
    private String cs;

    /**
     * 区县
     */
    private String qx;

    /**
     * 详细地址
     */
    private String xxdz;

    /**
     * 退款理由
     */
    private String tkly;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date zfsj;

    /**
     * 申请退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sqtksj;

    /**
     * 退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tksj;

    /**
     * 取消时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date qxsj;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;
}