package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CdEnum {


    CDDMBGL("cddmbgl","菜单代码表管理"),

    CZDMBGL("czdmbgl","操作代码表管理"),

    DMGL("dmgl","代码管理表"),

    SJBWH("sjbwh","数据表维护"),

    ZDMHWH("zdmhwh","站点门户维护"),

    SJZDGL("sjzdgl","数据字典管理");

    private String key;

    private String msg;
}
