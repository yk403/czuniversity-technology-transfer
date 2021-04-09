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
    TECHNOLOGY_TRANSACTION_PORTAL("technology_transaction_portal", "技术交易门户"),

    /**
     * 技术交易后台管理
     */
    TECHNOLOGY_TRANSACTION_BACKSTAGE_MANAGEMENT("technology_transaction_backstage_management", "技术交易后台管理"),

    /**
     * 人才培养门户
     */
    TALENT_TRAINING_PORTAL("talent_training_portal", "人才培养门户"),

    /**
     * 人才培养后台管理
     */
    TALENT_TRAINING_BACKSTAGE_MANAGEMENT("talent_training_backstage_management", "人才培养后台管理");

    private String key;

    private String msg;
}
