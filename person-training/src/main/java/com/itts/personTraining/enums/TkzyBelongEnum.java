package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 题库资源所属
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TkzyBelongEnum {

    MY("my", "个人资源");

    private String key;

    private String msg;
}
