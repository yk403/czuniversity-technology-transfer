package com.itts.personTraining.controller.kcsj.user;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.kcsl.KcsjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itts.common.constant.SystemConstant.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/v1/kcsj")
@Api(value = "KcsjUserController", tags = "课程时间")
public class KcsjUserController {
    @Autowired
    private KcsjService kcsjService;

    /**
     * 获取列表 - 分页
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表 - 分页")
    public ResponseUtil list(@ApiParam(value = "当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "父级机构ID")@RequestParam(value = "fjjgId", required = false) Long fjjgId) {

        PageInfo pageInfo = kcsjService.fingByPage(pageNum, pageSize, fjjgId);

        return ResponseUtil.success(pageInfo);
    }
}
