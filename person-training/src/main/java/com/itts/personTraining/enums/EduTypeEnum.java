package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 教育类型枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EduTypeEnum {

    ACADEMIC_DEGREE_EDUCATION("A","学历学位教育"),

    VOCATIONAL_EDUCATION("B","职业教育"),

    ADULT_EDUCATION("C","继续教育");

    private String key;

    private String msg;
}
