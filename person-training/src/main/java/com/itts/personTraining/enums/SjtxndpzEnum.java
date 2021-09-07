package com.itts.personTraining.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum SjtxndpzEnum {
    EASY("easy","易"),
    COMMONLY("commonly","中"),
    DIFFCULTY("difficulty","难");


    private String key;

    private String value;
}
