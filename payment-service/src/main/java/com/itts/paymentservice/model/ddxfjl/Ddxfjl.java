package com.itts.paymentservice.model.ddxfjl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单消费记录
 * </p>
 *
 * @author liuyingming
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_ddxfjl")
public class Ddxfjl implements Serializable {

    private static final long serialVersionUID = -5713899608944962699L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 是否删除
     */
    private Boolean sfsc;

    /**
     * 支付时间
     */
    private Date zfsj;

    /**
     * 申请退款时间
     */
    private Date sqtksj;

    /**
     * 退款时间
     */
    private Date tksj;

    /**
     * 取消时间
     */
    private Date qxsj;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 创建人
     */
    private Long cjr;

    /**
     * 更新时间
     */
    private Date gxsj;

    /**
     * 更新人
     */
    private Long gxr;


}
