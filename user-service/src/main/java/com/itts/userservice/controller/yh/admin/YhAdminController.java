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
import com.itts.userservice.service.jggl.JgglService;
import com.itts.userservice.service.yh.YhService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
                                   @RequestParam(value = "groupId", required = false) Long groupId) {
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

        PageInfo<YhDTO> byPage = yhService.findByPage(pageNum, pageSize, type, group);
        return ResponseUtil.success(byPage);
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
        Yh Yh = yhService.get(id);
        return ResponseUtil.success(Yh);
    }

    /**
     * 新增
     *
     * @author fl
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Yh Yh) throws WebException {
        //检查参数是否合法
        checkRequest(Yh);
        Yh add = yhService.add(Yh);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     *
     * @author fl
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/{id}")
    public ResponseUtil update(@PathVariable("id") Long id, @RequestBody Yh Yh) throws WebException {
        //检查参数是否合法
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        Yh Yh1 = yhService.get(id);
        if (Yh1 == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //检查参数是否合法
        checkRequest(Yh);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(Yh, Yh1, "id", "chsj", "cjr");
        yhService.update(Yh1);
        return ResponseUtil.success(Yh1);

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

        Yh Yh = yhService.get(id);

        if (Yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        //设置删除状态，更新删除时间
        Yh.setSfsc(true);
        Yh.setGxsj(new Date());
        //更新
        yhService.update(Yh);

        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkRequest(Yh Yh) throws WebException {
        //如果参数为空，抛出异常
        if (Yh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

