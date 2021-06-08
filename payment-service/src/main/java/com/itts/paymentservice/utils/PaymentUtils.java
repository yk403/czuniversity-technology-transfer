package com.itts.paymentservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
public class PaymentUtils {

    /**
     * 生成订单编号
     */
    public static String generateOrderNo() {

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timeStr = dateTimeFormatter.format(localDateTime);

        int randomNum = (int) ((Math.random() * 9 + 1) * 100000);

        return "CD" + timeStr + randomNum;
    }
}