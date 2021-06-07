package com.itts.personTraining.controller.kssj.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.request.kssj.AddKssjRequest;
import com.itts.personTraining.request.kssj.UpdateKssjRequest;
import com.itts.personTraining.service.kssj.KssjService;
import com.itts.personTraining.vo.kssj.GetKssjVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 考试试卷 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-14
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/kssj")
@Api(tags = "考试试卷后台管理")
public class KssjAdminController {

    @Autowired
    private KssjService kssjService;

    @ApiOperation(value = "列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "教育类型") @RequestParam(value = "educationType", required = false) String educationType,
                             @ApiParam(value = "学员类型") @RequestParam(value = "studentType", required = false) String studentType,
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) Long courseId,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition) {

        PageHelper.startPage(pageNum, pageSize);

        List kssjs = kssjService.list(new QueryWrapper<Kssj>()
                .eq("sfsc", false)
                .eq(StringUtils.isNotBlank(educationType), "jylx", educationType)
                .eq(StringUtils.isNotBlank(studentType), "xylx", studentType)
                .eq(courseId == null, "kc_id", courseId)
                .like(StringUtils.isNotBlank(condition), "sjmc", condition)
                .orderByDesc("cjsj"));

        PageInfo<Kssj> pageInfo = new PageInfo<>(kssjs);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        GetKssjVO kssj = kssjService.get(id);

        if (kssj == null) {

            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(kssj);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddKssjRequest addKssjRequest) {

        checkAddRequest(addKssjRequest);

        Kssj kssj = kssjService.add(addKssjRequest);

        return ResponseUtil.success(kssj);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody UpdateKssjRequest updateKssjRequest) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        checkUpdateRequest(updateKssjRequest);

        Kssj old = kssjService.getOne(new QueryWrapper<Kssj>().eq("sfsc", false).eq("id", updateKssjRequest.getId()));
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (!Objects.equals(old.getCjr(), loginUser.getUserId())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }

        Kssj kssj = kssjService.update(updateKssjRequest, old, loginUser.getUserId());

        return ResponseUtil.success(kssj);
    }

    @ApiOperation(value = "更新状态")
    @PutMapping("/update/status/{id}")
    public ResponseUtil updateStatus(@ApiParam(value = "主键ID") @PathVariable("id") Long id,
                                     @ApiParam(value = "是否上架") @RequestParam("sfsj") Boolean sfsj) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Kssj old = kssjService.getOne(new QueryWrapper<Kssj>().eq("sfsc", false).eq("id", id));

        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (!Objects.equals(old.getCjr(), loginUser.getUserId())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }

        Date now = new Date();

        old.setSfsj(sfsj);
        old.setSjsj(now);
        old.setGxr(loginUser.getUserId());
        old.setGxsj(now);

        kssjService.updateById(old);

        return ResponseUtil.success(old);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable(value = "id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Kssj old = kssjService.getOne(new QueryWrapper<Kssj>().eq("sfsc", false).eq("id", id));

        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (!Objects.equals(old.getCjr(), loginUser.getUserId())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }

        Date now = new Date();

        old.setSfsc(true);
        old.setGxr(loginUser.getUserId());
        old.setGxsj(now);

        kssjService.updateById(old);

        return ResponseUtil.success(old);
    }

    /**
     * 校验新增参数
     */
    private void checkAddRequest(AddKssjRequest addKssjRequest) {

        if (addKssjRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addKssjRequest.getSjmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (CollectionUtils.isEmpty(addKssjRequest.getTmIds())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addKssjRequest.getSjzf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addKssjRequest.getPdzf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addKssjRequest.getDanzf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addKssjRequest.getDuozf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addKssjRequest.getSjlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 校验更新参数
     */
    private void checkUpdateRequest(UpdateKssjRequest updateKssjRequest) {

        if (updateKssjRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateKssjRequest.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateKssjRequest.getSjmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (CollectionUtils.isEmpty(updateKssjRequest.getTmIds())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateKssjRequest.getSjzf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateKssjRequest.getPdzf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateKssjRequest.getDanzf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateKssjRequest.getDuozf() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateKssjRequest.getSjlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

