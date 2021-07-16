package com.itts.userservice.enmus;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum HuaWeiAssetTypeEnum {

    BASE_INFO("base_info", "媒资基本信息"),

    TRANSCODE_INFO("transcode_info", "转码结果信息"),

    THUMBNAIL_INFO("thumbnail_info", "截图结果信息"),

    REVIEW_INFO("review_info", "审核结果信息");

    private String key;

    private String value;
}
