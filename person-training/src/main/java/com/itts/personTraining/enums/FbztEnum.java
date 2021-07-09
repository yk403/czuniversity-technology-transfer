package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 发布状态枚举类
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum FbztEnum {

    UN_RELEASE("unrelease","未发布"),

    RELEASE("release","已发布"),

    STOP_USING("stop","停止使用");

    private String key;
    private String msg;
}
