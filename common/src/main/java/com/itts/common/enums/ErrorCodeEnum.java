package com.itts.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 错误码枚举
 *
 * @param
 * @author liuyingming
 * @description 错误码为6位， 前三位服务码， 后三位接口码
 * @return
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCodeEnum {

    //====================      技术交易服务错误提示（服务码410）      ====================
    TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR(-410001, "请求参数不合法"),
    TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR(-410002, "数据不存在"),
    //====================      技术交易服务错误提示  END     ====================


    //====================       支付服务错误提示（服务码420）      ====================
    PAY_FAIL_ERROR(-420001, "支付失败"),
    //====================       支付服务错误提示END     ====================

    //====================       文件上传错误提示（服务码430）      ====================
    UPLOAD_FAIL_ERROR(-430001, "文件上传失败");
    //====================       文件上传错误提示END     ====================
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示信息
     */
    private String msg;
}
