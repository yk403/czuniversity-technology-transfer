package com.itts.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SystemTypeEnum {

    /**
     * 技术交易门户
     */
    TECHNOLOGY_TRANSACTION_PORTAL("TECHNOLOGY_TRANSACTION_PORTAL", "技术交易门户"),

    /**
     * 技术交易后台管理
     */
    TECHNOLOGY_TRANSACTION_BACKSTAGE_MANAGEMENT("TECHNOLOGY_TRANSACTION_BACKSTAGE_MANAGEMENT", "技术交易后台管理"),

    /**
     * 人才培养门户
     */
    TALENT_TRAINING_PORTAL("TALENT_TRAINING_PORTAL", "人才培养门户"),

    /**
     * 人才培养后台管理
     */
    TALENT_TRAINING_BACKSTAGE_MANAGEMENT("TALENT_TRAINING_BACKSTAGE_MANAGEMENT", "人才培养后台管理");

    private String key;

    private String msg;
}
