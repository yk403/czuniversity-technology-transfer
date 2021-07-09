package com.itts.common.utils.common;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.itts.common.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class ResponseUtil {

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 错误码， 0 - 成功
     */
    private Integer errCode;

    /**
     * 数据
     */
    private Object data;

    /**
     * 成功， 传入错误码、错误信息、数据
     */
    public static ResponseUtil success(Integer errCode, String errMsg, Object data) {
        return new ResponseUtil(errMsg, errCode, data);
    }

    /**
     * 成功， 传入数据
     */
    public static ResponseUtil success(Object data) {
        return success(0, "success", data);
    }

    /**
     * 成功，无参
     */
    public static ResponseUtil success() {
        return success(0, "success", null);
    }

    /**
     * 成功， 传入错误信息、数据
     */
    public static ResponseUtil success(String errMsg, Object data) {
        return success(0, errMsg, data);
    }

    /**
     * 成功， 传入错误信息
     */
    public static ResponseUtil success(String errMsg) {
        return success(0, errMsg, null);
    }

    /**
     * 失败， 传入错误码、错误信息、数据
     */
    public static ResponseUtil error(Integer errCode, String errMsg, Object data) {
        return new ResponseUtil(errMsg, errCode, data);
    }

    /**
     * 失败， 传入错误码、错误信息
     */
    public static ResponseUtil error(Integer errCode, String errMsg) {
        return error(errCode, errMsg, null);
    }

    /**
     * 失败， 传入错误枚举
     */
    public static ResponseUtil error(ErrorCodeEnum errorCodeEnum) {
        return error(errorCodeEnum.getCode(), errorCodeEnum.getMsg(), null);
    }

    /**
     * 数据转换
     */
    public <T> T conversionData(TypeReference<T> typeReference) {

        return JSON.parseObject(JSONUtil.toJsonPrettyStr(data), typeReference);
    }
}