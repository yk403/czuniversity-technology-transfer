package com.itts.personTraining.controller.jjrpxjh.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.DateUtils;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.request.jjrpxjh.AddJjrpxjhRequest;
import com.itts.personTraining.request.jjrpxjh.UpdateJjrpxjhRequest;
import com.itts.personTraining.service.jjrpxjh.JjrpxjhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.itts.common.enums.ErrorCodeEnum.GROUNDING_EXISTS_ERROR;

/**
 * <p>
 * 经纪人培训计划表 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-01
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/jjrpxjh")
@Api(tags = "经纪人培训计划管理")
public class JjrpxjhAdminController {

    @Autowired
    private JjrpxjhService jjrpxjhService;

    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "名称") @RequestParam(value = "mc", required = false) String mc,
                             @ApiParam(value = "批次") @RequestParam(value = "pcId", required = false) Long pcId,
                             @ApiParam(value = "父级机构ID")@RequestParam(value = "fjjgId", required = false) Long fjjgId) {

        PageHelper.startPage(pageNum, pageSize);

        List<Jjrpxjh> list = jjrpxjhService.list(new QueryWrapper<Jjrpxjh>()
                .eq("sfsc", false)
                .eq(fjjgId != null,"fjjg_id", fjjgId)
                .like(StringUtils.isNotBlank(mc), "pxjhmc", mc)
                .eq(pcId != null, "pc_id", pcId).orderByDesc("cjsj"));

        PageInfo pageInfo = new PageInfo(list);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Jjrpxjh jjrpxjh = jjrpxjhService.getById(id);
        if (jjrpxjh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(jjrpxjh);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddJjrpxjhRequest addJjrpxjhRequest) {
        checkAddRequest(addJjrpxjhRequest);

        Jjrpxjh jjrpxjh = jjrpxjhService.add(addJjrpxjhRequest);

        return ResponseUtil.success("新增成功！",jjrpxjh);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody UpdateJjrpxjhRequest updateJjrpxjhRequest) {

        checkUpdateRequest(updateJjrpxjhRequest);

        Jjrpxjh old = jjrpxjhService.getById(updateJjrpxjhRequest.getId());
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        Jjrpxjh jjrpxjh = jjrpxjhService.update(old, updateJjrpxjhRequest);

        return ResponseUtil.success("更新成功！",jjrpxjh);
    }

    @ApiOperation(value = "更新状态")
    @PutMapping("/update/status/{id}")
    public ResponseUtil updateStatus(@PathVariable("id") Long id, @RequestParam("sfsj") Boolean sfsj) {

        Jjrpxjh old = jjrpxjhService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        int size = jjrpxjhService.list(new QueryWrapper<Jjrpxjh>().eq("sfsc", false).eq("sfsj", true)).size();
        if (size == 1 && sfsj) {
            throw new ServiceException(GROUNDING_EXISTS_ERROR);
        }

        Jjrpxjh jjrpxjh = jjrpxjhService.updateStatus(old, sfsj);

        return ResponseUtil.success(jjrpxjh);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        Jjrpxjh old = jjrpxjhService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        jjrpxjhService.delete(old);

        return ResponseUtil.success();
    }

    /**
     * 校验新增参数是否合法
     */
    private void checkAddRequest(AddJjrpxjhRequest addJjrpxjhRequest) {

        if (addJjrpxjhRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addJjrpxjhRequest.getPxjhmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addJjrpxjhRequest.getXymc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addJjrpxjhRequest.getPcId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addJjrpxjhRequest.getPcMc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addJjrpxjhRequest.getSkkssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addJjrpxjhRequest.getSkjssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addJjrpxjhRequest.getBmkssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addJjrpxjhRequest.getBmjssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addJjrpxjhRequest.getDd())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addJjrpxjhRequest.getJylx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addJjrpxjhRequest.getXylx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 校验更新参数是否合法
     */
    private void checkUpdateRequest(UpdateJjrpxjhRequest updateJjrpxjhRequest) {

        if (updateJjrpxjhRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateJjrpxjhRequest.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateJjrpxjhRequest.getPxjhmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateJjrpxjhRequest.getXymc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateJjrpxjhRequest.getPcId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateJjrpxjhRequest.getPcMc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateJjrpxjhRequest.getSkkssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateJjrpxjhRequest.getSkjssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateJjrpxjhRequest.getBmkssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (updateJjrpxjhRequest.getBmjssj() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateJjrpxjhRequest.getDd())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateJjrpxjhRequest.getJylx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(updateJjrpxjhRequest.getXylx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

