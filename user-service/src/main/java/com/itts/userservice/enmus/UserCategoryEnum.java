package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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

    public static String getMsgByKey(String key) {

        for (UserCategoryEnum value : UserCategoryEnum.values()) {

            if (StringUtils.equals(value.getKey(), key)) {
                return value.getMsg();
            }
        }

        return null;
    }
}