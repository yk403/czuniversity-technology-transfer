package com.itts.personTraining.controller.zj.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.model.zj.Zj;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.zj.ZjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.PROFESSOR;
import static com.itts.personTraining.enums.UserTypeEnum.SCHOOL_LEADER;

/**
 * <p>
 * 专家表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-05-25
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/zj")
@Api(value = "ZjAdminController", tags = "专家后台管理")
public class ZjAdminController {

    @Autowired
    private ZjService zjService;

    /**
     * 查询专家列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "yjly", required = false) String yjly,
                                   @RequestParam(value = "name", required = false) String name) {
        return ResponseUtil.success(zjService.findByPage(pageNum, pageSize, yjly, name));
    }

    /**
     * 根据id查询专家信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询专家信息")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(zjService.get(id));
    }

    /**
     * 根据姓名/电话查询专家信息
     *
     * @param xm
     * @param dh
     * @return
     */
    @GetMapping("/getByXmDh")
    @ApiOperation(value = "根据姓名/电话查询专家信息")
    public ResponseUtil getByXmDh(@RequestParam(value = "xm", required = false) String xm,
                                  @RequestParam(value = "dh", required = false) String dh) {
        if (xm == null && dh == null) {
            throw new WebException(NAME_PHONE_ISEMPTY_ERROR);
        }
        return ResponseUtil.success(zjService.getByXmDh(xm,dh));
    }

    /**
     * 查询所有专家
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "查询所有专家")
    public ResponseUtil getAll() {
        return ResponseUtil.success(zjService.getAll());
    }

    /**
     * 新增专家
     *
     * @param zj
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增专家")
    public ResponseUtil add(@RequestBody Zj zj, HttpServletRequest request) throws WebException {
        //检查参数是否合法
        checkAddRequest(zj);
        if (!zjService.add(zj,request.getHeader("token"))) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增专家成功!");
    }

    /**
     * 更新专家信息
     * @param zj
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新专家信息")
    public ResponseUtil update(@RequestBody Zj zj) throws WebException {
        //检查数据库中是否存在要更新的数据
        Zj zjOld = zjService.get(zj.getId());
        if (zjOld == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        checkUpdateRequest(zj);
        if (!zjService.update(zj)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新专家成功!");

    }

    /**
     * 根据id删除专家信息
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "根据id删除专家信息")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        Zj zj = zjService.get(id);
        if (zj == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!zjService.delete(zj)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除专家信息成功!");
    }

    /**
     * 新增校验参数
     */
    private void checkAddRequest(Zj zj) throws WebException {
        if (zj == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (zj.getDh() == null) {
            throw new WebException(PHONE_NUMBER_ISEMPTY_ERROR);
        }
        if (zj.getBh() == null) {
            throw new WebException(PROFESSOR_NUMBER_ISEMPTY_ERROR);
        }
        if (!zj.getLx().equals(SCHOOL_LEADER.getKey()) && !zj.getLx().equals(PROFESSOR.getKey())) {
            throw new WebException(PROFESSOR_TYPE_ERROR);
        }
        List<Zj> zjList = zjService.getAll();
        for (Zj zj1 : zjList) {
            if (zj1.getDh().equals(zj.getDh())) {
                throw new WebException(PROFESSOR_PHONE_EXISTS_ERROR);
            }
            if (zj1.getBh().equals(zj.getBh())) {
                throw new WebException(PROFESSOR_NUMBER_EXISTS_ERROR);
            }
        }
    }

    /**
     * 更新校验参数
     */
    private void checkUpdateRequest(Zj zj) throws WebException {
        Zj zjOld = zjService.get(zj.getId());
        if (zjOld == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!zjOld.getDh().equals(zj.getDh())) {
            List<Zj> zjList = zjService.getAll();
            for (Zj zj1 : zjList) {
                if (zj1.getDh().equals(zj.getDh())) {
                    throw new WebException(PROFESSOR_PHONE_EXISTS_ERROR);
                }
                if (zj1.getBh().equals(zj.getBh())) {
                    throw new WebException(PROFESSOR_NUMBER_EXISTS_ERROR);
                }
            }
        }
    }
}

