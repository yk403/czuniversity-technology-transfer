package com.itts.userservice.response.thirdparty;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@Data
public class GetAssetInfoResponse implements Serializable {

    private static final long serialVersionUID = -2198695889214319680L;

    /**
     * 媒资ID
     */
    @Alias(value = "asset_id")
    private String assetId;

    /**
     * 媒资基本信息
     */
    @Alias(value = "base_info")
    private BaseInfo baseInfo;

    /**
     * 转码生成文件信息
     */
    @Alias(value = "transcode_info")
    private TranscodeInfo transcodeInfo;

    /**
     * 截图信息
     */
    @Alias(value = "thumbnail_info")
    private ThumbnailInfo thumbnailInfo;

    /**
     * 审核信息数组
     */
    @Alias(value = "review_info")
    private ReviewInfo reviewInfo;

    /**
     * 媒资基本信息
     */
    @Data
    public static class BaseInfo implements Serializable {

    }

    /**
     * 转码生成文件信息
     */
    @Data
    public static class TranscodeInfo {

        /**
         * 转码模板组名称
         */
        @Alias(value = "template_group_name")
        private String templateGroupName;

        /**
         * 转码输出数组
         */
        private List<Output> output;

        /**
         * 转码状态。
         * UN_TRANSCODE：未转码
         * WAITING_TRANSCODE：待转码
         * TRANSCODING：转码中
         * TRANSCODE_SUCCEED：转码成功
         * TRANSCODE_FAILED：转码失败
         */
        @Alias(value = "transcode_status")
        private String transcodeStatus;

        /**
         * 执行情况描述
         */
        @Alias(value = "exec_desc")
        private String execDesc;
    }

    /**
     * 转码输出数组。
     * HLS或DASH格式：此数组的成员个数为n+1，n为转码输出路数。
     * MP4格式：此数组的成员个数为n，n为转码输出路数。
     */
    @Data
    public static class Output implements Serializable {

        private static final long serialVersionUID = 5522007861230117688L;

        /**
         * 协议类型。
         * hls
         * dash
         * mp4
         */
        @Alias(value = "play_type")
        private String playType;

        /**
         * 播放URL
         */
        private String url;

        /**
         * 标记流是否已被加密。
         * 0：表示未加密。
         * 1：表示已被加密。
         */
        private int encrypted;

        /**
         * 清晰度。
         * FLUENT：流畅
         * SD：标清
         * HD：高清
         * FULL_HD：超清
         */
        private String quality;
    }

    /**
     * 截图信息
     */
    @Data
    public static class ThumbnailInfo implements Serializable {

        private static final long serialVersionUID = -3673262618230473058L;
    }

    /**
     * 审核信息数组
     */
    @Data
    private static class ReviewInfo implements Serializable {

        private static final long serialVersionUID = 1836395024690071460L;
    }
}