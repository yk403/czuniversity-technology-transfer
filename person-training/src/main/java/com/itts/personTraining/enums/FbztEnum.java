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

    UN_RELEASE("1","未发布"),

    RELEASE("2","已发布"),

    STOP_USING("3","停止使用");

    private String key;
    private String msg;
}
