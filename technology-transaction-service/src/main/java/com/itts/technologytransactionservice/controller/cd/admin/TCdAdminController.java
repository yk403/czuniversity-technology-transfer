package com.itts.technologytransactionservice.controller.cd.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TCd;
import com.itts.technologytransactionservice.service.cd.TCdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试Controller
 *
 * @author lym
 * @description
 * @since 2021-03-12
 */
@Api(tags = "获取tcd")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/tCd")
public class TCdAdminController {

    @Autowired
    private TCdService service;

    /**
     * 获取列表
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil find(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        PageInfo<TCd> page = service.findByPage(pageNum, pageSize);

        return ResponseUtil.success(page);
    }

    /**
     * 获取详情
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {

        TCd result = service.get(id);

        return ResponseUtil.success(result);
    }

    /**
     * 新增
     *
     * @param
     * @return
     * @author liuyingming
     */
    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody TCd tCd) throws WebException {

        checkRequest(tCd);

        TCd result = service.add(tCd);
        return ResponseUtil.success(result);
    }

    /**
     * 更新
     *
     * @param
     * @return
     * @author liuyingming
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id") Long id, @RequestBody TCd tCd) throws WebException {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        TCd old = service.get(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR);
        }

        checkRequest(tCd);

        BeanUtils.copyProperties(tCd, old, "id");
        service.update(old);
        return ResponseUtil.success(old);
    }

    /**
     * 更新状态
     *
     * @param
     * @return
     * @author liuyingming
     */
    @ApiOperation(value = "更新状态")
    @PutMapping("/update/status/{id}")
    public ResponseUtil updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status) throws WebException {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        TCd old = service.get(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_NOT_FIND_ERROR);
        }

        //TODO：修改状态操作
        return ResponseUtil.success();
    }

    /**
     * 删除
     *
     * @param
     * @return
     * @author liuyingming
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {

        if (id == null) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //TODO: 逻辑删除， 修改delete_flag字段
        return ResponseUtil.success();
    }


    @GetMapping("/test/feign/")
    public ResponseUtil testFeign() {

        ResponseUtil test = service.testFeign();

        return test;
    }


    /**
     * 校验参数是否合法
     */
    private void checkRequest(TCd tCd) throws WebException {

        if (tCd == null) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(tCd.getCdbm())) {
            throw new WebException(ErrorCodeEnum.TECHNOLOGY_TRANSACTION_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

