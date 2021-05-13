package com.itts.personTraining.controller.ks.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KsDTO;
import com.itts.personTraining.service.ks.KsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 考试表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-05-06
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/ks")
@Api(value = "KsAdminController", tags = "考试后台管理")
public class KsAdminController {
    @Autowired
    private KsService ksService;

    /**
     * 查询考试列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "获取考试列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "kslx", required = false) String kslx,
                                   @RequestParam(value = "pcId", required = false) Long pcId,
                                   @RequestParam(value = "pclx", required = false) String pclx,
                                   @RequestParam(value = "kcmc", required = false) String kcmc) {
        return ResponseUtil.success(ksService.findByPage(pageNum, pageSize, kslx, pcId, pclx, kcmc));
    }

    /**
     * 根据id查询考试详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询考试详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(ksService.get(id));
    }

    /**
     * 新增考试
     *
     * @param ksDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增考试")
    public ResponseUtil add(@RequestBody KsDTO ksDTO) throws WebException {
        //检查参数是否合法
        checkRequest(ksDTO);
        if (!ksService.add(ksDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增考试成功!");
    }

    /**
     * 更新考试
     *
     * @param ksDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新考试")
    public ResponseUtil update(@RequestBody KsDTO ksDTO) throws WebException {
        Long id = ksDTO.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (ksService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!ksService.update(ksDTO)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新考试成功!");

    }

    /**
     * 删除考试
     *
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除考试")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        KsDTO ksDTO = ksService.get(id);
        if (ksDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!ksService.delete(ksDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除考试成功!");
    }

    /**
     * 考试批量下发
     *
     * @param ids
     * @return
     */
    @PutMapping("/update/issueBatch")
    @ApiOperation(value = "考试批量下发")
    public ResponseUtil issueBatch(@RequestBody List<Long> ids) {
        if (!ksService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("考试统一下发成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(KsDTO ksDTO) throws WebException {
        if (ksDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (ksDTO.getKcExps() == null) {
            throw new WebException(COURSE_ISEMPTY_ERROR);
        }
    }
}

