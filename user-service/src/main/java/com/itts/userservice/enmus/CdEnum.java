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

    SJZDGL("sjzdgl","数据字典管理"),

    XXGL("xxgl","信息管理"),
    JSZB("jszb","技术招标"),
    JSPM("jspm","技术拍卖"),
    JSGP("jsgp","技术挂牌"),
    SCLY("scly","双创路演"),

    ZHGL("zhgl","综合管理"),
    RLZYGL("rlzygl","人力资源管理"),
    YTHJX("ythjx","一体化教学"),
    SZHXXZX("szhxxzx","数字化学习中心");

    private String key;

    private String msg;
}
