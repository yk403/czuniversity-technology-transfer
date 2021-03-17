package com.itts.common.exception;

import com.itts.common.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * controller层异常
 *
 * @param
 * @author liuyingming
 * @return
 */
@Data
public class WebException extends RuntimeException {

    private Integer code;

    private String msg;

    public WebException() {
        super();
    }

    public WebException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WebException(Integer code) {
        this(code, null);
    }

    public WebException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public WebException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public WebException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}
