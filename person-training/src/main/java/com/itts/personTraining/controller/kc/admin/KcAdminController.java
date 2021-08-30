package com.itts.personTraining.controller.kc.admin;

import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.KcDTO;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.service.kc.KcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 课程表 前端控制器
 * </p>
 *
 * @author Austin
 * @since 2021-04-20
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/kc")
@Api(value = "KcAdminController", tags = "课程后台管理")
public class KcAdminController {

    @Autowired
    private KcService kcService;

    /**
     * 查询课程列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "查询课程列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "kclx", required = false) String kclx,
                                   @RequestParam(value = "name", required = false) String name,
                                   @RequestParam(value = "jylx", required = false) String jylx,
                                   @RequestParam(value = "xylx", required = false) String xylx,
                                   @RequestParam(value = "fjjgId", required = false) Long fjjgId,
                                   @RequestParam(value = "userType", required = false) String userType) {
        return ResponseUtil.success(kcService.findByPage(pageNum, pageSize, kclx, name,jylx,xylx,fjjgId,userType));
    }

    /**
     * 根据id查询课程详情
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id查询课程详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        return ResponseUtil.success(kcService.get(id));
    }

    /**
     * 根据条件查询课程
     */
    @GetMapping("/getByCondition")
    @ApiOperation(value = "根据条件查询课程")
    public ResponseUtil getByCondition(@RequestParam(value = "xylx", required = false) String xylx) {
        return ResponseUtil.success(kcService.getByCondition(xylx));
    }

    /**
     * 新增课程
     *
     * @param kcDTO
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增课程")
    public ResponseUtil add(@RequestBody KcDTO kcDTO) throws WebException {
        //检查参数是否合法
        checkRequest(kcDTO);
        if (!kcService.add(kcDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增课程成功!");
    }

    /**
     * 更新课程
     *
     * @param kcDTO
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新课程")
    public ResponseUtil update(@RequestBody KcDTO kcDTO) throws WebException {
        Long id = kcDTO.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (kcService.get(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!kcService.update(kcDTO)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新课程成功!");

    }

    /**
     * 删除课程
     * @param id
     * @return
     * @throws WebException
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除课程")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        KcDTO kcDTO = kcService.get(id);
        if (kcDTO == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新删除状态
        if (!kcService.delete(kcDTO)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除课程成功!");
    }

    /**
     * 课程批量下发
     * @param ids
     * @return
     */
    @PutMapping("/update/issueBatch")
    @ApiOperation(value = "课程批量下发")
    public ResponseUtil issueBatch(@RequestBody List<Long> ids) {
        if (!kcService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("课程统一下发成功!");
    }
    /**
     * 校验参数
     */
    private void checkRequest(KcDTO kcDTO) throws WebException {
        if (kcDTO == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        /*if(kcDTO.getSzIds() == null) {
            throw new WebException(TEACHER_ISEMPTY_ERROR);
        }*/
        if (kcDTO.getKcdm() == null) {
            throw new WebException(TEACH_TYPE_ISEMPTY_ERROR);
        }
        List<KcDTO> kcList = kcService.getByCondition(null);

        for (KcDTO kcDTO1 : kcList) {
            if (kcDTO1.getKcdm().equals(kcDTO.getKcdm()) || kcDTO1.getKcmc().equals(kcDTO.getKcmc())) {
                throw new WebException(COURSE_EXISTS_ERROR);
            }
        }
    }
}

