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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.personTraining.enums.UserTypeEnum.POSTGRADUATE;

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
     * 查询学员列表
     *
     * @return
     */
    @GetMapping("/getlist")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pcId", required = false) Long pcId,
                                   @RequestParam(value = "xslbmc", required = false) String xslbmc,
                                   @RequestParam(value = "jyxs", required = false) String jyxs,
                                   @RequestParam(value = "name", required = false) String name) {
        return ResponseUtil.success(xsService.findExport(pcId, xslbmc, jyxs, name));
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
     * 查询所有学员详情
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "根据所有学员详情")
    public ResponseUtil getAll() {
        return ResponseUtil.success(xsService.getAll());
    }

    /**
     * 根据条件查询学员详情
     *
     * @param xh
     * @return
     */
    @GetMapping("/getByCondition")
    @ApiOperation(value = "根据条件查询学员详情")
    public ResponseUtil selectByCondition(@RequestParam(value = "xh",required = false) String xh,
                                       @RequestParam(value = "lxdh",required = false) String lxdh,
                                         @RequestParam(value = "yhId",required = false) Long yhId) {
        if (xh == null && lxdh == null && yhId == null) {
            throw new WebException(CONDITION_IS_EMPTY_ERROR);
        }
        return ResponseUtil.success(xsService.selectByCondition(xh,lxdh,yhId));
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
     * 新增学员(外部调用)
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
    public ResponseUtil update(@RequestBody StuDTO stuDTO,HttpServletRequest request) throws WebException {
        Long id = stuDTO.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xsService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!xsService.update(stuDTO,request.getHeader("token"))) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学员成功!");
    }

    /**
     * 更新学员(外部调用)
     * @param xs
     * @return
     * @throws WebException
     */
    @PutMapping("/updateXs")
    @ApiOperation(value = "更新学员(外部调用)")
    public ResponseUtil updateXs(@RequestBody Xs xs) throws WebException {
        if (!xsService.updateXs(xs)) {
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
        List<Long> pcIds = stuDTO.getPcIds();
        if (pcIds == null || pcIds.size() == 0) {
            throw new WebException(BATCH_NUMBER_ISEMPTY_ERROR);
        }
        String xh = stuDTO.getXh();
        String lxdh = stuDTO.getLxdh();
        if (StringUtils.isEmpty(xh) && StringUtils.isEmpty(lxdh)) {
            throw new WebException(NUMBER_AND_PHONE_ISEMPTY_ERROR);
        }
        if (!StringUtils.isEmpty(xh)) {
            checkXsExist(xh, null);
        } else {
            checkXsExist(null, lxdh);
        }
        if(stuDTO.getJgId() == null) {
            throw new WebException(ORGANIZATION_ISEMPTY_ERROR);
        }
        //checkLxdh(stuDTO);
    }




    /**
     * 校验参数
     */
    private void checkRequestUser(StuDTO stuDTO) throws WebException {
        if (stuDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        List<StuDTO> stuDTOList = xsService.getAll();
        for (StuDTO stu : stuDTOList) {
            if (stuDTO.getYhId().equals(stu.getYhId())) {
                throw new WebException(USER_EXISTS_ERROR);
            }
        }
        //checkLxdh(stuDTO);
    }

    /**
     * 校验学号
     * @param stuDTO
     */
    private void checkLxdh(StuDTO stuDTO) {
        List<StuDTO> stuDTOList = xsService.getAll();
        for (StuDTO stu : stuDTOList) {
            if (stu.getLxdh().equals(stuDTO.getLxdh())) {
                throw new WebException(PHONE_NUMBER_EXISTS_ERROR);
            }
        }
    }

    /**
     * 校验学生信息是否存在
     * @param xh
     * @param lxdh
     */
    public void checkXsExist(String xh, String lxdh) {
        StuDTO byXh = xsService.selectByCondition(xh, lxdh,null);
        if (byXh != null) {
            List<Long> pcIds1 = byXh.getPcIds();
            if (pcIds1 != null && pcIds1.size() > 0) {
                throw new WebException(STUDENT_MSG_EXISTS_ERROR);
            }
        }
    }

}

