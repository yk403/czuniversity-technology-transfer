package com.itts.personTraining.controller.pk.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.PkDTO;
import com.itts.personTraining.model.pk.Pk;
import com.itts.personTraining.service.pk.PkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                   @RequestParam(value = "pcId", required = false) Long pcId) {
        return ResponseUtil.success(pkService.findByPage(pageNum, pageSize, pcId));
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
     * @param pkDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增排课")
    public ResponseUtil add(@RequestBody PkDTO pkDTO) throws WebException {
        //检查参数是否合法
        checkRequest(pkDTO);
        if (!pkService.add(pkDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增排课成功!");
    }

    /**
     * 批量新增排课
     *
     * @param pkDTOs
     * @return
     * @throws WebException
     */
    @PostMapping("/addList")
    @ApiOperation(value = "批量新增排课")
    public ResponseUtil add(@RequestBody List<PkDTO> pkDTOs) throws WebException {
        //检查参数是否合法
        for (PkDTO pkDTO : pkDTOs) {
            checkRequest(pkDTO);
        }
        if (!pkService.addList(pkDTOs)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增排课成功!");
    }

    /**
     * 更新排课
     *
     * @param pkDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新排课")
    public ResponseUtil update(@RequestBody PkDTO pkDTO) throws WebException {
        Long id = pkDTO.getId();
        //检查参数是否合法
        if (id == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (pkService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!pkService.update(pkDTO)) {
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
        PkDTO pkDTO = pkService.get(id);
        if (pkDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!pkService.delete(pkDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除排课成功!");
    }

    /**
     * 校验参数
     */
    private void checkRequest(PkDTO pkDTO) throws WebException {
        if (pkDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (pkDTO.getId() != null) {
            throw new WebException(SCHEDUING_EXISTS_ERROR);
        }
    }
}

