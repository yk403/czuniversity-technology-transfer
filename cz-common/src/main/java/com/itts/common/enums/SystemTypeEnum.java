package com.itts.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SystemTypeEnum {

    /**
     * 技术交易
     */
    TECHNOLOGY_TRANSACTION("technology_transaction", "技术交易"),

    /**
     * 人才培养门户
     */
    TALENT_TRAINING("talent_training", "人才培养"),

    USER("user", "用户统一管理");

    private String key;

    private String msg;
}
