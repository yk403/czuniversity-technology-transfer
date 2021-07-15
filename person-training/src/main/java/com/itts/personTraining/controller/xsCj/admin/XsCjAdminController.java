package com.itts.personTraining.controller.xsCj.admin;


import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.dto.StuDTO;
import com.itts.personTraining.dto.XsCjDTO;
import com.itts.personTraining.feign.userservice.UserFeignService;
import com.itts.personTraining.mapper.pcXs.PcXsMapper;
import com.itts.personTraining.model.xsCj.XsCj;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.pcXs.PcXsService;
import com.itts.personTraining.service.xsCj.XsCjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 学生成绩表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-06-01
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/xsCj")
@Api(value = "XsCjAdminController", tags = "学生成绩后台管理")
public class XsCjAdminController {
    @Autowired
    private XsCjService xsCjService;
    @Autowired
    private KcService kcService;
    @Resource
    private PcXsMapper pcXsMapper;
    @Resource
    private UserFeignService userFeignService;

    /**
     * 分页查询学生成绩列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询学生成绩列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "pcId", required = false) Long pcId,
                                   @RequestParam(value = "xh", required = false) String xh,
                                   @RequestParam(value = "xm", required = false) String xm,
                                   @RequestParam(value = "xymc", required = false) String xymc,
                                   @RequestParam(value = "jylx", required = false) String jylx) {
        return ResponseUtil.success(xsCjService.findByPage(pageNum, pageSize, pcId, xh, xm, xymc, jylx));
    }

    /**
     * 根据id查询学生成绩详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询学生成绩详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(xsCjService.get(id));
    }

    /**
     * 根据批次id查询所有学生成绩
     */
    @GetMapping("/getByPcId/{pcId}")
    @ApiOperation(value = "根据批次id查询所有学生成绩")
    public ResponseUtil getByPcId(@PathVariable("pcId") Long pcId) {
        return ResponseUtil.success(xsCjService.getByPcId(pcId));
    }

    /**
     * 新增学生成绩
     *
     * @param xsCjDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增学生成绩")
    public ResponseUtil add(@RequestBody XsCjDTO xsCjDTO) throws WebException {
        //检查参数是否合法
        checkRequest(xsCjDTO);
        if (!xsCjService.add(xsCjDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增学生成绩成功!");
    }

    /**
     * 更新学生成绩
     *
     * @param xsCjDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新学生成绩")
    public ResponseUtil update(@RequestBody XsCjDTO xsCjDTO) throws WebException {
        Long id = xsCjDTO.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (xsCjService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!xsCjService.update(xsCjDTO)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新学生成绩成功!");

    }

    /**
     * 删除学生成绩
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除学生成绩")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        XsCjDTO xsCjDTO = xsCjService.get(id);
        if (xsCjDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!xsCjService.delete(xsCjDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除学生成绩成功!");
    }

    /**
     * 学生成绩批量下发
     *
     * @param ids
     * @return
     */
    @PutMapping("/update/issueBatch")
    @ApiOperation(value = "学生成绩批量下发")
    public ResponseUtil issueBatch(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new WebException(STUDENT_NOT_SELECTED);
        }
        if (!xsCjService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("学生成绩统一下发成功!");
    }



    /**
     * 校验参数
     */
    private void checkRequest(XsCjDTO xsCjDTO) throws WebException {
        if (xsCjDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Long pcId = xsCjDTO.getPcId();
        if (pcId == null) {
            throw new WebException(BATCH_ID_ISEMPTY_ERROR);
        }
        //判断该学生成绩是否已存在,存在则返回提示消息
        List<XsCj> xsCjs = xsCjService.getXsCjByPcId(pcId);
        for (XsCj xsCj : xsCjs) {
            if (xsCj.getPcId().equals(pcId) && xsCj.getXsId().equals(xsCjDTO.getXsId())) {
                throw new WebException(BATCH_STUDENT_EXISTS_ERROR);
            }
        }

    }
}

