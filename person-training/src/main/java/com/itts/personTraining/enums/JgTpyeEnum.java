package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum JgTpyeEnum {

    HEADQUARTERS("headquarters","总基地"),

    BRANCH("branch","分基地");

    private String key;

    private String msg;
}
