package com.itts.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：lym
 * @Description：相应数据工具类
 * @Date: 2021/3/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtil {

    private String errMsg;

    private Integer errCode;

    private Object data;

    public static ResponseUtil success(Integer errCode, String errMsg, Object data) {
        return new ResponseUtil(errMsg, errCode, data);
    }

    public static ResponseUtil success(Object data) {
        return success(0, "success", data);
    }

    public static ResponseUtil success() {
        return success(0, "success", null);
    }

    public static ResponseUtil success(String errMsg, Object data) {
        return success(0, errMsg, data);
    }

    public static ResponseUtil success(String errMsg) {
        return success(0, errMsg, null);
    }

    public static ResponseUtil error(Integer errCode, String errMsg, Object data) {
        return new ResponseUtil(errMsg, errCode, data);
    }

    public static ResponseUtil error(Integer errCode, String errMsg) {
        return error(errCode, errMsg, null);
    }
}