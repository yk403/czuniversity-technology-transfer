package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 用户类型枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserTypeEnum {
    POSTGRADUATE("postgraduate","研究生"),

    BROKER("broker","经纪人"),

    TUTOR("tutor","研究生导师"),

    CORPORATE_MENTOR("corporate_mentor","企业导师"),

    TEACHER("teacher","任课教师");

    private String key;

    private String msg;
}
