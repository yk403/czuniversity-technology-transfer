package com.itts.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author liuyingming
 * @version : TraceIdUtils.java, v0.1 2020/9/14 qyl Exp $$
 */
public class TraceIdUtils {

    private static final String TRACE_ID = "traceId";

    private static final ThreadLocal<String> TRACE_ID_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取HTTPServletRequest对象
     *
     * @return
     */
    private static HttpServletRequest getRequest() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) attributes).getRequest();
    }

    /**
     * 获取当前traceId，优先从当前HTTP请求中获取，如请求中不存在，则自动生成一个并保存至当前HTTP请求作用域中，<br/>
     * 如当前线程不是HTTP请求线程，则从当前线程中获取，如未获取，则自动生成一个，并保存至当前线程中
     *
     * @return
     */
    public static String getTraceId() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return getTraceIdFromRequest(request);
        }

        String traceId = TRACE_ID_THREAD_LOCAL.get();
        if (traceId != null) {
            return traceId;
        }

        traceId = UUID.randomUUID().toString().replaceAll("-", "");
        TRACE_ID_THREAD_LOCAL.set(traceId);
        return traceId;
    }

    /**
     * 设置traceId，优先从当前HTTP请求作用域中设置，如当前线程不是HTTP请求线程，则保存至当前线程中
     *
     * @param traceId 要设置的跟踪id
     */
    public static void setTraceId(String traceId) {
        traceId = StringUtils.isBlank(traceId) ? TraceIdUtils.getTraceId() : traceId;
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(TRACE_ID, traceId);
            return;
        }
        TRACE_ID_THREAD_LOCAL.set(traceId);
    }

    private static String getTraceIdFromRequest(HttpServletRequest request) {
        Object obj = request.getAttribute(TRACE_ID);
        if (obj == null) {
            String traceId = UUID.randomUUID().toString().replaceAll("-", "");
            request.setAttribute(TRACE_ID, traceId);
            return traceId;
        }
        return obj.toString();
    }
}
