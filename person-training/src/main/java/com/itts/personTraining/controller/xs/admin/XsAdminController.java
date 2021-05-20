package com.itts.personTraining.controller.xs.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.service.jg.JgService;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
@RequestMapping(ADMIN_BASE_URL + "/v1/xs")
@Api(value = "XsAdminController", tags = "学生后台管理")
public class XsAdminController {

    @Autowired
    private XsService xsService;
    @Autowired
    private JgService jgService;

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
                                   @RequestParam(value = "xslbmc", required = false) String xslbmc,
                                   @RequestParam(value = "jyxs", required = false) String jyxs,
                                   @RequestParam(value = "name", required = false) String name) {
        return ResponseUtil.success(xsService.findByPage(pageNum, pageSize, pcId, xslbmc, jyxs, name));
    }

    /**
     * 获取机构列表
     */
    @GetMapping("/getJgList")
    @ApiOperation(value = "获取机构列表")
    public ResponseUtil getlist(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @ApiParam(value = "机构编码") @RequestParam(value = "jgbm",required = false) String jgbm,
                                HttpServletRequest request){
        return jgService.getlist(pageNum,pageSize,jgbm,request.getHeader("token"));
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
     * 根据条件查询学员详情
     *
     * @param xh
     * @return
     */
    @GetMapping("/getByCondition")
    @ApiOperation(value = "根据条件查询学员详情")
    public ResponseUtil getByCondition(@RequestParam(value = "xh",required = false) String xh) {
        return ResponseUtil.success(xsService.selectByCondition(xh));
    }

    /**
     * 新增学员
     *
     * @param stuDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学员")
    public ResponseUtil add(@RequestBody StuDTO stuDTO,HttpServletRequest request) throws WebException {
        //检查参数是否合法
        checkRequest(stuDTO);
        if (!xsService.add(stuDTO,request.getHeader("token"))) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学员成功!");
    }

    /**
     * 新增学员
     *
     * @param stuDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/addUser")
    @ApiOperation(value = "新增学员(外部调用)")
    public ResponseUtil addUser(@RequestBody StuDTO stuDTO) throws WebException {
        //检查参数是否合法
        checkRequestUser(stuDTO);
        if (!xsService.addUser(stuDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学员成功!");
    }

    /**
     * 更新学员
     *
     * @param stuDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学员")
    public ResponseUtil update(@RequestBody StuDTO stuDTO) throws WebException {
        Long id = stuDTO.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xsService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!xsService.update(stuDTO)) {
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
        StuDTO stuDTO = xsService.get(id);
        if (stuDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!xsService.delete(stuDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除学员成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(StuDTO stuDTO) throws WebException {
        if (stuDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //checkXh(stuDTO);
    }



    /**
     * 校验参数
     */
    private void checkRequestUser(StuDTO stuDTO) throws WebException {
        if (stuDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        List<Xs> xs = xsService.selectByCondition(stuDTO.getXh());
        for (Xs x : xs) {
            if (stuDTO.getYhId().equals(x.getYhId())) {
                throw new WebException(USER_EXISTS_ERROR);
            }
        }
        //checkXh(stuDTO);
    }

    /**
     * 校验学号
     * @param stuDTO
     */
    private void checkXh(StuDTO stuDTO) {
        List<Xs> xs = xsService.selectByCondition(null);
        for (Xs x : xs) {
            if (x.getXh().equals(stuDTO.getXh())) {
                throw new WebException(STUDENT_NUMBER_EXISTS_ERROR);
            }
        }
    }

}

