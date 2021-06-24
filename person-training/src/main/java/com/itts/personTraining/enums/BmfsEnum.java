package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 报名方式枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum BmfsEnum {

    ON_LINE("1","线上"),

    OFF_LINE("2","线下");

    private String key;

    private String msg;
}
