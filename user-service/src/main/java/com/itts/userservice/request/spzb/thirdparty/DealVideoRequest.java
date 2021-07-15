package com.itts.userservice.request.spzb.thirdparty;

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
public class DealVideoRequest implements Serializable {

    private static final long serialVersionUID = 1557941403578664040L;

    /**
     * 媒资ID
     */
    @Alias(value = "asset_id")
    private String assetId;

    /**
     * 转码模板组名称
     */
    @Alias(value = "template_group_name")
    private String templateGroupName;

    /**
     * 是否自动加密。
     * 0：表示不加密。
     * 1：表示需要加密。
     * 默认值：0。
     */
    @Alias(value = "auto_encrypt")
    private int autoEncrypt;

    /**
     * 截图参数
     */
    private Thumbnail thumbnail;

    /**
     * 字幕文件ID
     */
    @Alias(value = "subtitle_id")
    private List<Integer> subtitleId;

    /**
     * 截图参数
     * 截图可以跟转码一起，也可以独立
     */
    @Data
    public static class Thumbnail implements Serializable {

        private static final long serialVersionUID = -4391709456004578339L;

        /**
         * 截图类型。
         * time：每次进行截图的间隔时间。
         * dots : 按照指定的时间点截图
         */
        private String type;

        /**
         * 生成截图的时间间隔值。
         * 取值范围：[1,12]之间的整数。
         * 单位：秒
         */
        private int time;

        /**
         * 指定时间截图时的时间点数组
         */
        private List<Integer> dots;

        /**
         * 指定第几张截图作为封面
         * 默认值：1
         */
        @Alias(value = "cover_position")
        private int coverPosition;

        /**
         * 截图文件格式
         * 1：jpg
         * 默认值：1
         */
        private int format;

        /**
         * 纵横比，图像缩放方式  * 取值如下：
         * 0：自适应（保持原有宽高比）。
         * 1：16:9。
         * 默认值：0
         */
        @Alias(value = "aspect_ratio")
        private int aspectRatio;

        /**
         * 截图最长边的尺寸
         * 单位：像素
         * 宽边尺寸按照该尺寸与原始视频像素等比缩放计算。
         */
        @Alias(value = "max_length")
        private int maxLength;
    }
}