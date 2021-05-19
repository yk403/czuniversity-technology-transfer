package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/5/19
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserCategoryEnum {

    POSTGRADUATE("postgraduate", "研究生"),

    BROKER("broker", "经纪人"),

    TUTOR("tutor", "导师"),

    CORPORATE_MENTOR("corporate_mentor", "企业导师"),

    TEACHER("teacher", "任课教师"),

    ADMINISTRATOR("administrator", "管理员"),

    SCHOOL_LEADER("school_leader", "校领导");

    private String key;

    private String msg;
}