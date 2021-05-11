package com.itts.personTraining.controller.xxjs.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxjs.Xxjs;
import com.itts.personTraining.service.xxjs.XxjsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 学校教室表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-22
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/xxjs")
@Api(value = "XxjsAdminController", tags = "学校教室管理")
public class XxjsAdminController {
    @Autowired
    private XxjsService xxjsService;

    /**
     * 查询学校教室列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "jxlmc", required = false) String jxlmc) {
        return ResponseUtil.success(xxjsService.findByPage(pageNum, pageSize, jxlmc));
    }

    /**
     * 根据id查询学校教室详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(xxjsService.get(id));
    }

    /**
     * 新增学校教室
     *
     * @param xxjs
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学校教室")
    public ResponseUtil add(@RequestBody Xxjs xxjs) throws WebException {
        //检查参数是否合法
        checkRequest(xxjs);
        if (!xxjsService.add(xxjs)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学校教室成功!");
    }

    /**
     * 更新学校教室
     *
     * @param xxjs
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学校教室")
    public ResponseUtil update(@RequestBody Xxjs xxjs) throws WebException {
        checkRequest(xxjs);
        Long id = xxjs.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xxjsService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!xxjsService.update(xxjs)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学校教室成功!");

    }

    /**
     * 删除学校教室
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除学校教室")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xxjs xxjs = xxjsService.get(id);
        if (xxjs == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!xxjsService.delete(xxjs)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除学校教室成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(Xxjs xxjs) throws WebException {
        if (xxjs == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!xxjsService.selectExists(xxjs)) {
            throw new WebException(TEACHING_BUILDING_EXISTS_ERROR);
        }
    }
}

