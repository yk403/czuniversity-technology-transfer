package com.itts.userservice.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum HuaWeiTranscodeStatusEnum {

    UN_TRANSCODE("UN_TRANSCODE", "未转码"),
    WAITING_TRANSCODE("WAITING_TRANSCODE", "待转码"),
    TRANSCODING("TRANSCODING", "转码中"),
    TRANSCODE_SUCCEED("TRANSCODE_SUCCEED", "转码成功"),
    TRANSCODE_FAILED("TRANSCODE_FAILED","转码失败");

    private String key;

    private String value;


}
