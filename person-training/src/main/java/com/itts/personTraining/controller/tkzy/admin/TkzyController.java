package com.itts.personTraining.controller.tkzy.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.enums.TkzyBelongEnum;
import com.itts.personTraining.enums.TkzyTypeEnum;
import com.itts.personTraining.model.tkzy.Tkzy;
import com.itts.personTraining.request.tkzy.AddTkzyRequest;
import com.itts.personTraining.request.tkzy.UpdateTkzyRequest;
import com.itts.personTraining.service.tkzy.TkzyService;
import com.itts.personTraining.vo.tkzy.GetTkzyVO;
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
 * 题库资源 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-13
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/tkzy")
@Api(tags = "题库资源后台管理")
public class TkzyController {

    @Autowired
    private TkzyService tkzyService;

    @ApiOperation(value = "列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "一级分类") @RequestParam(value = "firstCategory", required = false) String firstCategory,
                             @ApiParam(value = "二级分类") @RequestParam(value = "secondCategory", required = false) String secondCategory,
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) Long courseId,
                             @ApiParam(value = "题库所属：my - 我的") @RequestParam(value = "belong", required = false) String belong,
                             @ApiParam(value = "题目类型: single_choice - 单选; multiple_choice - 多选;judgment - 判断") @RequestParam(value = "type", required = false) String type,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        PageHelper.startPage(pageNum, pageSize);

        QueryWrapper query = new QueryWrapper();
        query.eq("sfsc", false);

        if (StringUtils.isNotBlank(firstCategory)) {
            query.eq("tmyjfl", firstCategory);
        }

        if (StringUtils.isNotBlank(secondCategory)) {
            query.eq("tmejfl", secondCategory);
        }

        if (courseId != null) {
            query.eq("kc_id", courseId);
        }

        if (StringUtils.isNotBlank(type)) {
            query.eq("tmlx", type);
        }

        if (StringUtils.isNotBlank(belong) && Objects.equals(belong, TkzyBelongEnum.MY.getKey())) {

            if (loginUser == null) {
                throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
            }

            query.eq("cjr", loginUser.getUserId());
        }

        if (StringUtils.isBlank(condition)) {
            query.like("mc", condition);
        }

        List tkzys = tkzyService.list(query);

        PageInfo<Tkzy> pageInfo = new PageInfo<>(tkzys);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation("详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        GetTkzyVO tkzy = tkzyService.get(id);

        if (tkzy == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (tkzy.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(tkzy);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddTkzyRequest addTkzyRequest) {

        checkAddRequest(addTkzyRequest);

        Tkzy tkzy = tkzyService.add(addTkzyRequest);

        return ResponseUtil.success(tkzy);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody UpdateTkzyRequest updateTkzyRequest) {

        checkUpdateRequest(updateTkzyRequest);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Tkzy old = tkzyService.getById(updateTkzyRequest.getId());
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (old.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (!Objects.equals(loginUser.getUserId(), old.getCjr())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }

        Tkzy tkzy = tkzyService.update(updateTkzyRequest, old, loginUser.getUserId());

        return ResponseUtil.success(tkzy);
    }

    @ApiOperation(value = "更新状态")
    @PutMapping("/update/status/{id}")
    public ResponseUtil updateStatus(@ApiParam(value = "主键ID") @PathVariable(value = "id", required = false) Long id,
                                     @ApiParam(value = "是否上架") @RequestParam(value = "sfsj", required = false) Boolean sfsj) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Tkzy old = tkzyService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (old.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (!Objects.equals(loginUser.getUserId(), old.getCjr())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }

        Date now = new Date();

        old.setSfsj(sfsj);
        old.setSjsj(now);
        old.setGxsj(now);
        old.setGxr(loginUser.getUserId());

        tkzyService.updateById(old);

        return ResponseUtil.success(old);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Tkzy old = tkzyService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (old.getSfsc()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if (!Objects.equals(loginUser.getUserId(), old.getCjr())) {
            throw new WebException(ErrorCodeEnum.NOT_PERMISSION_ERROR);
        }

        old.setSfsc(true);
        old.setGxsj(new Date());
        old.setGxr(loginUser.getUserId());

        tkzyService.updateById(old);

        return ResponseUtil.success(old);
    }

    /**
     * 校验新增参数
     */
    private void checkAddRequest(AddTkzyRequest addTkzyRequest) {

        if (addTkzyRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addTkzyRequest.getMc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addTkzyRequest.getKcId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addTkzyRequest.getTmyjfl())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addTkzyRequest.getTmlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addTkzyRequest.getFz() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (!Objects.equals(addTkzyRequest.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())
                && CollectionUtils.isEmpty(addTkzyRequest.getTmxxs())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 校验更新参数
     */
    private void checkUpdateRequest(UpdateTkzyRequest updateTkzyRequest) {

        if (updateTkzyRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateTkzyRequest.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateTkzyRequest.getMc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateTkzyRequest.getKcId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateTkzyRequest.getTmyjfl())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateTkzyRequest.getTmlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateTkzyRequest.getFz() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (!Objects.equals(updateTkzyRequest.getTmlx(), TkzyTypeEnum.JUDGMENT.getKey())
                && CollectionUtils.isEmpty(updateTkzyRequest.getTmxxs())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

