package com.itts.personTraining.controller.xxzy.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.xxzy.AddXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 学习资源 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-12
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/xxzy/v1")
public class XxzyAdminController {

    @Autowired
    private XxzyService xxzyService;

    /**
     * 获取列表
     */
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "资源类型：in-内部资源；out-外部资源") @RequestParam(value = "type") String type,
                             @ApiParam(value = "一级分类") @RequestParam(value = "firstCategory", required = false) String firstCategory,
                             @ApiParam(value = "二级分类") @RequestParam(value = "secondCategory", required = false) String secondCategory,
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) String courseId,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition) {

        PageInfo<Xxzy> pageInfo = xxzyService.list(pageNum, pageSize, type, firstCategory, secondCategory, courseId, condition);

        return ResponseUtil.success(pageInfo);
    }

    /**
     * 获取详情
     */
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Xxzy xxzy = xxzyService.getById(id);

        if (xxzy == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (xxzy.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(xxzy);
    }

    /**
     * 新增
     */
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddXxzyRequest addXxzyRequest) {

        checkAddRequest(addXxzyRequest);

        Xxzy xxzy = xxzyService.add(addXxzyRequest);

        return ResponseUtil.success(xxzy);
    }

    /**
     * 校验新增参数是否合法
     */
    private void checkAddRequest(AddXxzyRequest addXxzyRequest) {

        if (addXxzyRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addXxzyRequest.getMc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addXxzyRequest.getZylx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addXxzyRequest.getZylx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

