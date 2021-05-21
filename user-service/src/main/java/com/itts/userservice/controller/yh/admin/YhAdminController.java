package com.itts.userservice.controller.yh.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.YhDTO;
import com.itts.userservice.enmus.UserTypeEnum;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.request.yh.AddYhRequest;
import com.itts.userservice.service.jggl.JgglService;
import com.itts.userservice.service.yh.YhService;
import com.itts.userservice.vo.yh.GetYhVO;
import com.itts.userservice.vo.yh.YhListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-18
 */
@Api(tags = "用户后台管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/yh")
@Slf4j
public class YhAdminController {

    @Autowired
    private YhService yhService;

    @Autowired
    private JgglService jgglService;

    /**
     * 获取列表 - 分页
     *
     * @param pageNum pageSize
     * @author fl
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "type", required = false) String type,
                                   @RequestParam(value = "groupId", required = false) Long groupId,
                                   @RequestParam(value = "condition", required = false) String condition) {
        Jggl group = null;

        if (groupId != null) {
            group = jgglService.get(groupId);

            if (group == null) {

                log.error("【用户管理 - 后台管理】查询用户列表，机构不存在，机构ID: ", groupId);
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }
        }

        if (StringUtils.isNotBlank(type) && (!UserTypeEnum.check(type))) {

            log.error("【用户管理 - 后台管理】查询用户列表，请求参数不合法: ", type);
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        PageInfo<YhListVO> byPage = yhService.findByPage(pageNum, pageSize, type, group, condition);
        return ResponseUtil.success(byPage);
    }

    /**
     * 获取指定列表 - 分页
     *
     * @param pageNum pageSize
     * @author fl
     */
    @GetMapping("/getlist/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil findByPage(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                   @RequestParam(value = "type") String type,
                                   @RequestParam(value = "string") String string) {
        if (StringUtils.isBlank(type)) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (string == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PageInfo<YhDTO> yhPageInfo = yhService.selectByString(pageNum, pageSize, type, string);
        return ResponseUtil.success(yhPageInfo);
    }

    /**
     * 获取详情
     *
     * @param id
     * @author fl
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Yh yh = yhService.get(id);

        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetYhVO getYhVO = new GetYhVO();
        BeanUtils.copyProperties(yh, getYhVO);

        return ResponseUtil.success(getYhVO);
    }

    @ApiOperation(value = "通过用户编号查询")
    @GetMapping("/get/by/code/")
    public ResponseUtil getByCode(@ApiParam("用户编号") @RequestParam("code") String code) {

        GetYhVO yh = yhService.getByCode(code);

        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(yh);
    }

    @ApiOperation(value = "通过用户手机号查询")
    @GetMapping("/get/by/phone/")
    public ResponseUtil getByPhone(@ApiParam("用户手机号") @RequestParam("phone") String phone) {

        GetYhVO yh = yhService.getByphone(phone);

        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(yh);
    }

    /**
     * 新增
     *
     * @author fl
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody AddYhRequest addYhRequest, HttpServletRequest request) throws WebException {

        //检查参数是否合法
        checkRequest(addYhRequest);

        GetYhVO result = yhService.add(addYhRequest, request.getHeader(SystemConstant.TOKEN_PREFIX));


        /*Yh Yh = new Yh();
        BeanUtils.copyProperties(addYhRequest, Yh);

        //检查参数是否合法
        List<Long> jsidlist = addYhRequest.getJsidlist();
        if (jsidlist == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        jsidlist.forEach(jsid -> {
            Boolean flag = yhService.addYhAndJsmc(Yh, jsid);
        });*/

        return ResponseUtil.success(result);
    }

    /**
     * 更新
     *
     * @author fl
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    @Transactional(rollbackFor = Exception.class)
    public ResponseUtil update(@RequestBody AddYhRequest addYhRequest) throws WebException {

        //检查参数是否合法
        if (addYhRequest.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addYhRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addYhRequest.getYhlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //检查数据库中是否存在要更新的数据
        Yh old = yhService.get(addYhRequest.getId());

        GetYhVO result = yhService.update(addYhRequest, old);

        return ResponseUtil.success(result);
    }

    @ApiOperation(value = "重置密码")
    @PutMapping("/reset/password/{id}")
    public ResponseUtil resetPassword(@PathVariable("id") Long id) {

        Yh yh = yhService.get(id);
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        yhService.resetPassword(yh);

        return ResponseUtil.success();
    }

    /**
     * 删除
     *
     * @author fl
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {

        //检查参数是否为空
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Yh yh = yhService.get(id);
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        yhService.delete(yh);

        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkRequest(AddYhRequest yh) throws WebException {

        //如果参数为空，抛出异常
        if (yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getYhlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if(StringUtils.equals(yh.getYhlx(), UserTypeEnum.IN_USER.getCode())){
            if (StringUtils.isBlank(yh.getYhlb())) {
                throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
            }
        }

        if (StringUtils.isBlank(yh.getYhm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(yh.getMm())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

