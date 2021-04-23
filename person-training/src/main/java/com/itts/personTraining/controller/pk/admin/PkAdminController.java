package com.itts.personTraining.controller.pk.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.service.pk.PkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 排课表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/pk")
@Api(value = "PkAdminController", tags = "排课后台管理")
public class PkAdminController {
    @Autowired
    private PkService pkService;

    /**
     * 查询排课列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pch", required = false) String pch) {
        return ResponseUtil.success(pkService.findByPage(pageNum, pageSize, pch));
    }

    /**
     * 根据id查询排课详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(pkService.get(id));
    }

    /**
     * 新增排课
     *
     * @param pk
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增排课")
    public ResponseUtil add(@RequestBody Pk pk) throws WebException {
        //检查参数是否合法
        checkRequest(pk);
        if (!pkService.add(pk)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增排课成功!");
    }

    /**
     * 更新排课
     *
     * @param pk
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新排课")
    public ResponseUtil update(@RequestBody Pk pk) throws WebException {
        Long id = pk.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (pkService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!pkService.update(pk)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新排课成功!");

    }

    /**
     * 删除排课
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除排课")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Pk pk = pkService.get(id);
        if (pk == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!pkService.delete(pk)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除排课成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(Pk pk) throws WebException {
        if (pk == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

