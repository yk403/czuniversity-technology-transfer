package com.itts.userservice.controller.sjzd.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.sjzd.Sjzd;
import com.itts.userservice.service.sjzd.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Austin
 * @Data: 2021/8/13
 * @Version: 1.0.0
 * @Description:
 */
@Api(tags = "数据字典-前台")
@Slf4j
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/sjzd")
public class SjzdUserController {
    @Resource
    private SjzdService sjzdService;

    /**
     * 获取列表
     */
    @GetMapping("/findList/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findList(@RequestParam(value = "xtlb", required = false) String xtlb,
                                @RequestParam(value = "mklx", required = false) String mklx,
                                @RequestParam(value = "ssmk", required = false) String ssmk) {

        List<Sjzd> bySsmk = sjzdService.findBySsmk(xtlb, mklx, ssmk);
        return ResponseUtil.success(bySsmk);
    }
}
