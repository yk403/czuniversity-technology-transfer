package com.itts.userservice.common.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试
 *
 * @author zs
 * @since 2020/3/25
 */
@Slf4j
@Component
public class TestTask {

    public void run() {
        log.info("执行成功");
    }

    public void run1(String str) {
        log.info("执行成功，参数为： {}" + str);
    }
}
