package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 题库资源类型枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TkzyTypeEnum {

    SINGLE_CHOICE("single_choice", "单选"),

    MULTIPLE_CHOICE("multiple_choice", "多选"),

    JUDGMENT("judgment", "判断");

    private String key;

    private String msg;
}
