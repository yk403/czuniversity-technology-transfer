package com.itts.common.model;

import lombok.Data;

/**
 * @Author: Austin
 * @Data: 2021/3/12
 * @Version: 0.0.1
 * @Description: 用于接收并转换 sms 返回的数据
 */
@Data
public class SmsResponse {
    private String Message;
    private String RequestId;
    private String Code;
    private String BizId;
}
