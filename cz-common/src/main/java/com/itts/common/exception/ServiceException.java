package com.itts.common.exception;

import com.itts.common.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * service层异常
 *
 * @param
 * @author liuyingming
 * @return
 */
@Data
public class ServiceException extends RuntimeException {

    private Integer code;
    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(Integer code) {
        this(code, null);
    }

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ServiceException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.msg = errorCodeEnum.getMsg();
    }
}
