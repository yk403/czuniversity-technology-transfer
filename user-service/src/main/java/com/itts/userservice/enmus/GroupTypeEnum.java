package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum GroupTypeEnum {

    HEADQUARTERS("headquarters", "总基地"),

    BRANCH("branch", "分基地"),

    OTHER("other", "其他");

    private String key;

    private String value;


}
