package com.itts.personTraining.controller.xxjxl.admin;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxjxl.Xxjxl;
import com.itts.personTraining.service.xxjxl.XxjxlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 学校教学楼表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-09-01
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/xxjxl")
@Api(value = "XxjxlAdminController", tags = "学校教学楼管理")
public class XxjxlAdminController {

    @Autowired
    private XxjxlService xxjxlService;

    /**
     * 查询学校教学楼列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "jxlmc", required = false) String jxlmc,
                                   @RequestParam(value = "fjjgId", required = false) Long fjjgId
                                   ) {
        return ResponseUtil.success(xxjxlService.findByPage(pageNum, pageSize, jxlmc,fjjgId));
    }

    /**
     * 根据id查询学校教学楼详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(xxjxlService.get(id));
    }

    /**
     * 新增学校教学楼
     *
     * @param xxjxl
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学校教学楼")
    public ResponseUtil add(@RequestBody Xxjxl xxjxl) throws WebException {
        //检查参数是否合法
        checkRequest(xxjxl);
        if (!xxjxlService.add(xxjxl)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学校教学楼成功!");
    }

    /**
     * 更新学校教学楼
     *
     * @param xxjxl
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学校教学楼")
    public ResponseUtil update(@RequestBody Xxjxl xxjxl) throws WebException {
        Long id = xxjxl.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xxjxlService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!xxjxlService.update(xxjxl)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学校教学楼成功!");

    }

    /**
     * 删除学校教学楼
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除学校教学楼")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Xxjxl xxjxl = xxjxlService.get(id);
        if (xxjxl == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!xxjxlService.delete(xxjxl)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除学校教学楼成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(Xxjxl xxjxl) throws WebException {
        if (xxjxl == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

