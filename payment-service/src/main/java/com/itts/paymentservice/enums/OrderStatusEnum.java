package com.itts.paymentservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OrderStatusEnum {

    PENDING("pending", "待支付"),
    PAID("paid", "已支付"),
    CANCELLED("cancelled", "已取消"),
    COMPLETED("completed", "已完成"),
    APPLY_REFUND("apply_refund", "申请退款"),
    REFUNDED("refunded", "已退款");

    private String key;

    private String value;

    public static OrderStatusEnum getByKey(String key) {

        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {

            if (Objects.equals(key, orderStatusEnum.getKey())) {
                return orderStatusEnum;
            }
        }

        return null;
    }
}