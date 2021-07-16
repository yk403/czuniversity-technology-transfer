package com.itts.userservice.service.spzb.thirdparty;

import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
public interface HuaWeiLiveService {

    /**
     * 获取Token
     */
    String getToken();

    /**
     * 处理视频
     */
    String dealLive(String assetId);

    /**
     * 通过ID获取媒资信息
     */
    GetAssetInfoResponse getVideoInfo(String assetId, String category);
}
