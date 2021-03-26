package com.itts.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SystemTypeEnum {

    /**
     * 技术交易门户
     */
    TECHNOLOGY_TRANSACTION_PORTAL("TECHNOLOGY_TRANSACTION_PORTAL", "技术交易门户"),

    /**
     * 人才培养门户
     */
    TALENT_TRAINING_PORTAL("TALENT_TRAINING_PORTAL", "人才培养门户ø");

    private String key;

    private String msg;
}
