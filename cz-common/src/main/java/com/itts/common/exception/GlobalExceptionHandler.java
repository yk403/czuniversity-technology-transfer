package com.itts.common.exception;

import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.TraceIdUtils;
import com.itts.common.utils.common.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @Author：lym
 * @Description：
 * @Date: 2021/3/15
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseUtil handle(Exception e) {

        String traceId = TraceIdUtils.getTraceId();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
            StringBuilder sb = new StringBuilder();
            List<ObjectError> errors = me.getBindingResult().getAllErrors();
            if (errors.size() > 1) {
                sb.append("共" + errors.size() + "个错误；");
            }
            for (ObjectError error : errors) {
                sb.append(error.getDefaultMessage()).append("，");
            }
            sb.delete(sb.length() - 1, sb.length());
            String errorMsg = sb.toString();
            log.warn("【参数非法】 param={} ex={} traceId={}", errorMsg, ExceptionUtils.getStackTrace(e), traceId);
            return ResponseUtil.builder().errCode(HttpStatus.BAD_REQUEST.value()).errMsg("参数错误:" + errorMsg).build();
        } else if (e instanceof HttpRequestMethodNotSupportedException) {

            log.warn("【请求方式错误】 ex={} traceId={}", ExceptionUtils.getStackTrace(e), traceId);
            return ResponseUtil.builder().errCode(ErrorCodeEnum.SYSTEM_REQUEST_METHOD_ERROR.getCode())
                    .errMsg(ErrorCodeEnum.SYSTEM_REQUEST_METHOD_ERROR.getMsg()).build();
        } else if (e instanceof HttpMessageNotReadableException) {

            HttpMessageNotReadableException le = (HttpMessageNotReadableException) e;
            log.error("【参数格式错误】 ex={} traceId={}", ExceptionUtils.getStackTrace(le), traceId);
            return ResponseUtil.builder().errCode(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_TYPE_ERROR.getCode())
                    .errMsg(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_TYPE_ERROR.getMsg()).build();
        } else if (e instanceof WebException) {

            WebException le = (WebException) e;
            log.error("【控制层异常】 ex={} traceId={}", ExceptionUtils.getStackTrace(le), traceId);
            return ResponseUtil.builder().errCode(le.getCode()).errMsg(le.getMsg()).build();
        } else if (e instanceof ServiceException) {

            ServiceException le = (ServiceException) e;
            log.error("【业务层异常】 ex={} traceId={}", ExceptionUtils.getStackTrace(le), traceId);
            return ResponseUtil.builder().errCode(le.getCode()).errMsg(le.getMsg()).build();
        } else {

            log.error("【系统异常】ex={}, traceId={}", ExceptionUtils.getStackTrace(e), traceId);
            return ResponseUtil.builder().errCode(ErrorCodeEnum.SYSTEM_ERROR.getCode())
                    .errMsg(ErrorCodeEnum.SYSTEM_ERROR.getMsg()).build();
        }
    }
}