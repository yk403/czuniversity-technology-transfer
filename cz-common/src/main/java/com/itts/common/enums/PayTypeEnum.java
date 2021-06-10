package com.itts.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 支付方式枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PayTypeEnum {

    ALIPAY("aLiPay", "支付宝"),
    WECHAT("wechat", "微信"),
    INTEGRAL("integral", "积分支付"),
    BALANCE("balance", "余额支付"),
    MIXING("mixing", "混合支付");

    private String key;

    private String value;

}
