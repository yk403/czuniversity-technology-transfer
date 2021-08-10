package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TypeEnum {

    IN("in", "内部"),

    OUT("out", "外部");

    private String code;

    private String msg;

    /**
     * 验证是否合法
     * @param type
     * @return
     */
    public static Boolean check(String type){

        for (UserTypeEnum value : UserTypeEnum.values()) {

            if(Objects.equals(type, value.getCode())){
                return true;
            }
        }

        return false;
    }
}
