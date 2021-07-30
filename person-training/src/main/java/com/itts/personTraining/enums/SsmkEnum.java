package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum SsmkEnum {

    USER_TYPE("user_type","学生类别"),
    POLITICS_STATUS("politics_status","政治面貌"),
    STUDY_TYPE("study_type","入学方式"),
    STUDY_MODE("study_mode","学习形式"),
    TECHNICAL_FIELD("technical_field","技术邻域");

    private String key;

    private String msg;
}
