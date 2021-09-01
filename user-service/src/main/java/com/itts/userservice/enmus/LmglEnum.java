package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LmglEnum {
    TALENT_TRAINING("talent_training","技术转移人才培养"),
    TECHNICAL_SERVICE("technical_service","技术转移服务"),
    TECHNOLOGY_TRANSACT("technology_transaction","技术交易市场"),
    BASE_CLOUD("base_cloud","基地云平台"),
    TRAINING_PLATFORM("training_platform","先进制造仿真实训平台");

    private String key;

    private String msg;
}
