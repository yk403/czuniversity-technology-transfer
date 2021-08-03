package com.itts.personTraining.controller.kcsj.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kcsj.Kcsj;
import com.itts.personTraining.service.kcsl.KcsjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程时间表 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-04-28
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/kcsj")
@Api(tags = "课程时间管理")
public class KcsjController {

    @Autowired
    private KcsjService kcsjService;

    /**
     * 获取列表 - 分页
     *
     * @param
     * @return
     * @author liuyingming
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表 - 分页")
    public ResponseUtil list(@ApiParam(value = "当前页数") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        PageInfo pageInfo = kcsjService.fingByPage(pageNum, pageSize);

        return ResponseUtil.success(pageInfo);
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

        Kcsj kcsj = kcsjService.getById(id);

        if (kcsj == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (kcsj.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(kcsj);
    }

    /**
     * 新增
     *
     * @param
     * @return
     * @author liuyingming
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Kcsj kcsj) {

        checkKcsj(kcsj);

        //获取数据库中排课时间，判断时间是否合法
        List<Kcsj> kcsjs = kcsjService.list(new QueryWrapper<Kcsj>().eq("sfsc",false));
        if (!CollectionUtils.isEmpty(kcsjs)) {

            for (Kcsj dbKcsj : kcsjs) {

                if ((kcsj.getId() != null) && (kcsj.getId().longValue() == dbKcsj.getId().longValue())) {
                    continue;
                }

                if (!checkSj(kcsj, dbKcsj)) {
                    throw new WebException(ErrorCodeEnum.COURSE_TIME_CONFLICT_ERROR);
                }
            }
        }

        Kcsj result = kcsjService.add(kcsj);
        return ResponseUtil.success(result);
    }

    /**
     * 更新
     *
     * @param
     * @return
     * @author liuyingming
     */
    @PutMapping("/update/")
    @ApiOperation(value = "更新")
    public ResponseUtil update(@RequestBody Kcsj kcsj) {

        if (kcsj.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        checkKcsj(kcsj);

        //获取数据库中排课时间，判断时间是否合法
        List<Kcsj> kcsjs = kcsjService.list(new QueryWrapper<Kcsj>().eq("sfsc",false));
        if (!CollectionUtils.isEmpty(kcsjs)) {

            for (Kcsj dbKcsj : kcsjs) {

                if ((kcsj.getId() != null) && (kcsj.getId().longValue() == dbKcsj.getId().longValue())) {
                    continue;
                }

                if (!checkSj(kcsj, dbKcsj)) {
                    throw new WebException(ErrorCodeEnum.COURSE_TIME_CONFLICT_ERROR);
                }
            }
        }

        Kcsj result = kcsjService.update(kcsj);

        return ResponseUtil.success(result);
    }

    /**
     * 删除
     *
     * @param
     * @return
     * @author liuyingming
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        Kcsj kcsj = kcsjService.getById(id);
        if (kcsj == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        kcsjService.delete(kcsj);

        return ResponseUtil.success();
    }

    /**
     * 校验参数是否正确
     */
    private void checkKcsj(Kcsj kcsj) {

        if (kcsj == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(kcsj.getKssj())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(kcsj.getJssj())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (kcsj.getKssj().compareTo(kcsj.getJssj()) >= 0) {

            throw new WebException(ErrorCodeEnum.START_TIME_GREATER_END_TIME_ERROR);
        }
    }

    /**
     * 校验课程时间是否合法
     *
     * @param
     * @return
     * @author liuyingming
     */
    private Boolean checkSj(Kcsj addKcsj, Kcsj dbKcsj) {

        //如果要添加的课程开始时间在已添加的课程时间区间内
        if (addKcsj.getKssj().compareTo(dbKcsj.getKssj()) >= 0 && addKcsj.getKssj().compareTo(dbKcsj.getJssj()) < 0) {
            return false;
        }

        //如果要添加的课程结束时间在已添加的课程时间区间内
        if (addKcsj.getJssj().compareTo(dbKcsj.getKssj()) > 0 && addKcsj.getJssj().compareTo(dbKcsj.getJssj()) < 0) {
            return false;
        }

        return true;
    }
}

