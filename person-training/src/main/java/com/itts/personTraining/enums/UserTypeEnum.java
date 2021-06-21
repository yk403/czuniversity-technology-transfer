package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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

    TEACHER("teacher","任课教师"),

    ADMINISTRATOR("administrator", "管理员"),

    SCHOOL_LEADER("school_leader", "校领导"),

    IN("in","内部用户");

    private String key;

    private String msg;
}