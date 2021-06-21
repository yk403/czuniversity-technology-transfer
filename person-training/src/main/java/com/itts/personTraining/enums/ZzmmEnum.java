package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 政治面貌枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ZzmmEnum {
    MEMBER("politics_status_01","团员"),

    PARTY_MEMBER("politics_status_02","党员"),

    ACTIVIST("politics_status_03","积极分子"),

    PRO_PARTY_MEMBER("politics_status_04","预备党员");

    private String key;

    private String msg;
}
