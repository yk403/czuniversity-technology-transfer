package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/4/27
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum VideoEnum {

    LIVE_BROADCAST("live_broadcast", "直播"),

    RECORDING("recording", "录播");

    private String code;

    private String msg;
}