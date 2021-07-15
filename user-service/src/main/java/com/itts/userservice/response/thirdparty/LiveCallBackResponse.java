package com.itts.userservice.response.thirdparty;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@Data
public class LiveCallBackResponse implements Serializable {

    private static final long serialVersionUID = 6664502473094847235L;

    /**
     * 项目ID
     */
    @Alias(value = "project_id")
    private String projectId;

    /**
     * 用于标识同一个文件。当“event_type”为“RECORD_NEW_FILE_START”或“RECORD_FILE_COMPLETE”时，携带该字段。
     */
    @Alias(value = "job_id")
    private String jobId;

    /**
     * 消息类型。
     * <p>
     * RECORD_START：表示录制开始。若配置了录制模板和录制回调，则直播推流开始时，开始录制，触发该事件。
     * RECORD_NEW_FILE_START：表示开始创建新的录制文件。以下情况会触发该事件：
     * 直播推流开始，开始创建第一个录制文件。
     * 直播断流恢复后，若“最大断流合并时长”配置为“断流后生成新文件”，则开始创建新的录制文件。
     * 当前录制时长超过了配置的录制周期，则开始新的录制文件生成。
     * RECORD_FILE_COMPLETE：表示录制文件生成完成。以下情况会触发该事件：
     * 录制时长达到配置的录制周期，则当前录制文件生成完成，并开始新的录制生成。
     * 直播断流后，若“最大断流合并时长”配置为“断流后生成新文件”，则当前录制文件生成完成，若断流恢复，将开始新的录制文件生成。
     * RECORD_OVER：表示录制结束，直播断流时长超过了最大断流合并时长，当前录制任务结束，触发该事件。
     * RECORD_FAILED：表示录制失败，当拉流失败、录制文件上传OBS失败等因素导致录制失败时，触发该事件。
     */
    @Alias(value = "event_type")
    private String eventType;

    /**
     * 直播推流域名
     */
    @Alias(value = "publish_domain")
    private String publishDomain;

    /**
     * 应用名
     */
    @Alias(value = "app")
    private String app;

    /**
     * 录制的流名
     */
    @Alias(value = "stream")
    private String stream;

    /**
     * 录制格式，支持HLS、FLV和MP4格式
     */
    @Alias(value = "record_format")
    private String recordFormat;

    /**
     * 录制文件的下载地址。当“event_type”为“RECORD_FILE_COMPLETE”时，携带该字段
     */
    @Alias(value = "download_url")
    private String downloadUrl;

    /**
     * 媒资ID
     */
    @Alias(value = "asset_id")
    private String assetId;

    /**
     * 录制文件播放地址，可以直接播放。当配置直播录制时，将存储录制文件的OBS桶授权给点播服务，且仅“event_type”为“RECORD_FILE_COMPLETE”时，携带该字段
     */
    @Alias(value = "play_url")
    private String playUrl;

    /**
     * 文件大小。
     * 单位：Byte。
     */
    @Alias(value = "file_size")
    private long fileSize;

    /**
     * 录制文件的时长
     * 单位：秒
     */
    @Alias(value = "record_duration")
    private long recordDuration;

    /**
     * 录制文件的开始时间，即接收到第一帧数据的时间，格式为yyyy-mm-ddThh:mm:ssZ。
     */
    @Alias(value = "start_time")
    private Date startTime;

    /**
     * 录制文件的结束时间，格式为yyyy-mm-ddThh:mm:ssZ。
     */
    @Alias(value = "end_time")
    private Date endTime;

    /**
     * 录制文件分辨率的宽
     */
    private int width;

    /**
     * 录制文件分辨率的高
     */
    private int height;

    /**
     * 存储录制文件的OBS桶所在区域
     */
    @Alias(value = "obs_location")
    private String obsLocation;

    /**
     * 存储录制文件的OBS桶
     */
    @Alias(value = "obs_bucket")
    private String obsBucket;

    /**
     * OBS桶存储录制文件的路径
     */
    @Alias(value = "obs_object")
    private String obsObject;

    /**
     * 事件通知签名。
     * MD5方式：auth_sign=MD5(key + auth_timestamp)
     * HMACSHA256方式：auth_sign=HMACSHA256(key + auth_timestamp,key)
     */
    @Alias(value = "auth_sign")
    private String authSign;

    /**
     * 事件通知签名过期UNIX时间戳
     */
    @Alias(value = "auth_timestamp")
    private String authTimestamp;

    /**
     * 录制失败的描述信息
     */
    @Alias(value = "error_message")
    private String errorMessage;
}