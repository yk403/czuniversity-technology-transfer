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

    //====================      系统错误提示（服务码400）      ====================
    SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR(-400001, "请求参数不合法"),
    SYSTEM_NOT_FIND_ERROR(-400002, "数据不存在"),
    //====================      系统错误提示  END     ====================

    //====================      技术交易服务错误提示（服务码410）      ====================
    /*TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR(-410001, "请求参数不合法"),
    TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR(-410002, "数据不存在"),*/
    //====================      技术交易服务错误提示  END     ====================


    //====================       支付服务错误提示（服务码420）      ====================
    PAY_FAIL_ERROR(-420001, "支付失败"),
    //====================       支付服务错误提示END     ====================

    //====================       登录登出错误提示（服务码430）      ====================
    NO_LOGIN_ERROR(-430001, "用户未登录"),
    NO_PERMISSION_ERROR(-430002, "用户无权限，请联系管理员"),
    LOGIN_USERNAME_PASSWORD_ERROR(-430003, "用户账号或密码错误，请重试"),
    NO_PERMISSION_LOGIN_USERNAME_PASSWORD_ERROR(-43004, "用户权限不足，请检查账号密码或联系管理员"),
    //====================       登录登出错误提示END     ====================

    //====================       用户服务错误提示（服务码440）      ====================
    USER_NOT_FIND_ERROR(-440001, "用户不存在"),
    //====================       用户服务错误提示END     ====================

    //====================       技术交易服务错误提示（服务码450）      ====================
    UPLOAD_FAIL_ERROR(-450001,"文件上传失败"),
    UPLOAD_FAIL_ISEMPTY_ERROR(-450002,"文件上传不可为空"),
    NAME_EXIST_ERROR(-45000,"名称已存在");
    //====================       用户服务错误提示END     ====================
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示信息
     */
    private String msg;
}
