package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum VideoStatusEnum {

    NOT_START("not_start", "未开始"),

    STARTED("started", "已开始"),

    FINISHED("finished", "已结束");

    private String key;

    private String value;
}
