package com.itts.personTraining.controller.xxzy.user;

import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/xxzy")
@Api(tags = "学习资源 - 门户")
public class XxzyController {

    @Autowired
    private XxzyService xxzyService;

    @ApiOperation(value = "列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "资源类型：in-内部资源；out-外部资源") @RequestParam(value = "type") String type,
                             @ApiParam(value = "一级分类") @RequestParam(value = "firstCategory", required = false) String firstCategory,
                             @ApiParam(value = "二级分类") @RequestParam(value = "secondCategory", required = false) String secondCategory,
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) Long courseId,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition,
                             HttpServletRequest request) {

        PageInfo<GetXxzyVO> result = xxzyService.listVO(pageNum, pageSize, type, firstCategory, secondCategory, courseId, condition, request.getHeader("token"));

        return ResponseUtil.success(result);

    }
}