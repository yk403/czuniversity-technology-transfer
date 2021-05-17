package com.itts.paymentservice.utils;

import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
@Data
public class TimeUtils {
    public static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+08:00").withZone(ZoneId.of("Asia/Shanghai"));
}
