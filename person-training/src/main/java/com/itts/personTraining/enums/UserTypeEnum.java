package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 用户类型枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserTypeEnum {
    POSTGRADUATE("postgraduate", "研究生"),

    BROKER("broker", "经纪人"),

    TUTOR("tutor", "研究生导师"),

    CORPORATE_MENTOR("corporate_mentor", "企业导师"),

    TEACHER("teacher", "任课教师"),

    CLOUD_ADMIN("cloud_admin","基地云平台管理员"),

    ADMINISTRATOR("administrator", "管理员"),

    SCHOOL_LEADER("school_leader", "校领导"),

    PROFESSOR("professor","内部"),

    OUT_PROFESSOR("out_professor","外部"),

    IN("in", "内部用户");

    private String key;

    private String msg;

    /**
     * 校验请求参数是否正确
     */
    public static boolean check(String key) {

        for (UserTypeEnum value : UserTypeEnum.values()) {

            if (Objects.equals(value.getKey(), key)) {

                return true;
            }
        }

        return false;
    }
}
