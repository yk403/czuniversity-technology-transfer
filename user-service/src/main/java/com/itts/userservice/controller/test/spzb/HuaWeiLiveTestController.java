package com.itts.userservice.controller.test.spzb;

import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.spzb.Spzb;
import com.itts.userservice.response.thirdparty.GetAssetInfoResponse;
import com.itts.userservice.service.spzb.thirdparty.HuaWeiLiveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/7/15
 */
@RestController
@RequestMapping(value = SystemConstant.BASE_URL + "/test/spzb")
public class HuaWeiLiveTestController {

    @Autowired
    private HuaWeiLiveService huaWeiLiveService;

    @GetMapping("/get/token/")
    public ResponseUtil testGetToken() {

        huaWeiLiveService.getToken();
        return ResponseUtil.success();
    }

    @GetMapping("/deal/video/")
    public ResponseUtil testDealVideo(@RequestParam("assetId") String assetId) {
        Spzb spzb=new Spzb();
        spzb.setMzId(assetId);
        String result = huaWeiLiveService.dealLive(spzb);
        if (StringUtils.isNotBlank(result)) {
            return ResponseUtil.success();
        }

        return ResponseUtil.error(ErrorCodeEnum.HUA_WEI_YUN_DEAL_VIDEO_ERROR);
    }

    @GetMapping("/get/asset/info/")
    public ResponseUtil getAssetInfo(@RequestParam("assetId") String assetId){

        GetAssetInfoResponse result = huaWeiLiveService.getVideoInfo(assetId, "transcode_info");
        return ResponseUtil.success(result);
    }
}