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
public enum JdlxEnum {
    HEADQUARTERS("headquarters","总基地"),

    BRANCH("branch","分基地");

    private String key;

    private String msg;
}