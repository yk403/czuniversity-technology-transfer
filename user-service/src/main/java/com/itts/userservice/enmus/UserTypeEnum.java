package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserTypeEnum {


    IN_USER("in", "内部用户"),

    OUT_USER("out", "外部用户");

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
