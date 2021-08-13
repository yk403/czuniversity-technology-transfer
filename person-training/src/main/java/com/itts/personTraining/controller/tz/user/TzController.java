package com.itts.personTraining.controller.tz.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.tz.TzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/23
 * @Version: 1.0.0
 * @Description: 通知前台
 */
@RestController
@RequestMapping(BASE_URL + "/v1/tz")
@Api(value = "TzController", tags = "通知-门户")
public class TzController {

    @Autowired
    private TzService tzService;

    /**
     * 根据用户类别查询通知信息(前)
     *
     * @return
     */
    @GetMapping("/findByCategory")
    @ApiOperation(value = "根据用户类别查询通知信息(前)")
    public ResponseUtil findByCategory(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "tzlx") String tzlx) {
        return ResponseUtil.success(tzService.findByCategory(pageNum,pageSize,tzlx));
    }

    /**
     * 根据通知id查询通知信息(前)
     *
     * @return
     */
    @GetMapping("/getTzDTO/{id}")
    @ApiOperation(value = "根据通知id查询通知信息(前)")
    public ResponseUtil getTzDTOById(@PathVariable(value = "id") Long id) {
        return ResponseUtil.success(tzService.getTzDTOById(id));
    }

    /**
     * 根据用户类别查询通知信息数
     *
     * @return
     */
    @GetMapping("/getTzCountByCategory")
    @ApiOperation(value = "根据通知id查询通知信息(前)")
    public ResponseUtil getTzCountByCategory() {
        return ResponseUtil.success(tzService.getTzCountByCategory());
    }

}
