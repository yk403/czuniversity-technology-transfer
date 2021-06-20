package com.itts.personTraining.controller.xsCj.user;

import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.ks.KsService;
import com.itts.personTraining.service.xsCj.XsCjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/6/8
 * @Version: 1.0.0
 * @Description: 学生成绩门户
 */
@RestController
@RequestMapping(BASE_URL + "/v1/xsCj")
@Api(value = "XsCjController", tags = "学生成绩-门户")
public class XsCjController {

    @Autowired
    private XsCjService xsCjService;

    /**
     * 根据用户id查询学生成绩详情(成绩通知)
     *
     * @return
     */
    @GetMapping("/getByYhId")
    @ApiOperation(value = "根据用户id查询学生成绩详情(成绩通知)")
    public ResponseUtil getByYhId() {
        return ResponseUtil.success(xsCjService.getByYhId());
    }

    /**
     * 根据用户id查询批次集合
     *
     * @return
     */
    @GetMapping("/findPcIdsByYhId")
    @ApiOperation(value = "根据学生id查询批次信息")
    public ResponseUtil findPcIdsByYhId() {
        return ResponseUtil.success(xsCjService.findPcByYhId());
    }

    /**
     * 查询学生成绩信息(综合信息)
     *
     * @return
     */
    @GetMapping("/getByCategory")
    @ApiOperation(value = "查询学生成绩信息(综合信息)")
    public ResponseUtil getByCategory(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                      @RequestParam(value = "pcId",required = false) Long pcId,
                                      @RequestParam(value = "name",required = false) String name) {
        return ResponseUtil.success(xsCjService.getByCategory(pageNum,pageSize,pcId,name));
    }

}
