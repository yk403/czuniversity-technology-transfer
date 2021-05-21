package com.itts.personTraining.controller.sz.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.enums.UserTypeEnum;
import com.itts.personTraining.model.sz.Sz;
import com.itts.personTraining.service.sz.SzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;

/**
 * <p>
 * 师资表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/sz")
@Api(value = "SzAdminController", tags = "师资后台管理")
public class SzAdminController {
    
    @Autowired
    private SzService szService;

    /**
     * 查询师资列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取师资列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "dsxm", required = false) String dsxm,
                                   @RequestParam(value = "dslb", required = false) String dslb,
                                   @RequestParam(value = "hyly", required = false) String hyly) {
        return ResponseUtil.success(szService.findByPage(pageNum, pageSize, dsxm, dslb, hyly));
    }

    /**
     * 根据id查询师资详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取师资详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(szService.get(id));
    }

    /**
     * 新增师资
     *
     * @param sz
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增师资")
    public ResponseUtil add(@RequestBody Sz sz, HttpServletRequest request) throws WebException {
        //检查参数是否合法
        checkRequest(sz);
        if (!szService.add(sz,request.getHeader("token"))) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增师资成功!");
    }

    /**
     * 新增师资(外部调用)
     *
     * @param sz
     * @return
     * @throws WebException
     */
    @PostMapping("/addSz")
    @ApiOperation(value = "新增师资")
    public ResponseUtil addSz(@RequestBody Sz sz) throws WebException {
        //检查参数是否合法
        checkSzRequest(sz);
        if (!szService.addSz(sz)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增师资成功!");
    }

    /**
     * 更新师资
     *
     * @param sz
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新师资")
    public ResponseUtil update(@RequestBody Sz sz) throws WebException {
        Long id = sz.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (szService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!szService.update(sz)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新师资成功!");

    }

    /**
     * 删除师资
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除师资")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Sz sz = szService.get(id);
        if (sz == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!szService.delete(sz)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除师资成功!");
    }

    /**
     * 校验参数
     */
    private void checkSzRequest(Sz sz) throws WebException {
        if (sz == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (sz.getDsbh() == null) {
            throw new WebException(TEACHER_NUMBER_ISEMPTY_ERROR);
        }
    }
    /**
     * 校验参数
     */
    private void checkRequest(Sz sz) throws WebException {
        if (sz == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (szService.selectByDsbh(sz.getDsbh()) != null) {
            throw new WebException(TEACHER_NUMBER_EXISTS_ERROR);
        }
        String dslb = sz.getDslb();
        if (dslb == null) {
            throw new WebException(TEACHER_TYPE_ISEMPTY_ERROR);
        }
        if (!dslb.equals(UserTypeEnum.TUTOR.getKey()) && !dslb.equals(UserTypeEnum.CORPORATE_MENTOR) && !dslb.equals(UserTypeEnum.TEACHER)) {
            throw new WebException(TEACHER_TYPE_ERROR);
        }
        if (sz.getSsjgId() == null) {
            throw new WebException(ORGANIZATION_ISEMPTY_ERROR);
        }
    }

}

