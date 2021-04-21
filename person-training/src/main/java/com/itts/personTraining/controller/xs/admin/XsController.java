package com.itts.personTraining.controller.xs.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@RestController
@Api(value = "XsController", tags = "学生后台管理")
@RequestMapping(ADMIN_BASE_URL + "/v1/xs")
public class XsController {

    @Autowired
    private XsService xsService;

    /**
     * 查询学员列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取学员列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pcId", required = false) Long pcId,
                                   @RequestParam(value = "xslbId", required = false) Long xslbId,
                                   @RequestParam(value = "jyxs", required = false) String jyxs) {
        return ResponseUtil.success(xsService.findByPage(pageNum, pageSize, pcId, xslbId, jyxs));
    }

    /**
     * 根据id查询学员详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取学员详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(xsService.get(id));
    }

    /**
     * 新增学员
     *
     * @param xs
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学员")
    public ResponseUtil add(@RequestBody Xs xs) throws WebException {
        //检查参数是否合法
        checkRequest(xs);
        if (!xsService.add(xs)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学员成功!");
    }

    /**
     * 更新学员
     *
     * @param xs
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学员")
    public ResponseUtil update(@RequestBody Xs xs) throws WebException {
        Long id = xs.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xsService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!xsService.update(xs)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学员成功!");

    }

    /**
     * 删除学员
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除学员")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xs xs = xsService.get(id);
        if (xs == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!xsService.delete(xs)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除学员成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(Xs xs) throws WebException {
        if (xs == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

}

