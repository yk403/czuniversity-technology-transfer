package com.itts.personTraining.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 课程类型枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CourseTypeEnum {

    ORIGINAL_PROFESSION_COURSE("1","原专业课程"),

    TECHNOLOGY_TRANSFER_COURSE("2","技术转移专业课程"),

    THEORY_CLASS("theory_class","理论课"),

    PRACTICE_COURSE("practice_course","实践课"),

    PRACTICAL_TRAINING("practical_training","实训课");

    private String key;

    private String msg;
}
