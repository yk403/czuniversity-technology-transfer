package com.itts.paymentservice.controller.ddxfjl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.paymentservice.enums.OrderStatusEnum;
import com.itts.paymentservice.model.ddxfjl.Ddxfjl;
import com.itts.paymentservice.request.ddxfjl.AddDdxfjlRequest;
import com.itts.paymentservice.service.DdxfjlService;
import com.itts.paymentservice.service.WxPatmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/2
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/ddxfjl")
@Api(tags = "订单消费记录")
public class DdxfjlController {

    @Autowired
    private DdxfjlService ddxfjlService;

    @Autowired
    private WxPatmentService wxPaymentService;

    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "订单编号") @RequestParam(value = "ddbh", required = false) String ddbh,
                             @ApiParam(value = "订单状态") @RequestParam(value = "zt", required = false) String zt) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Ddxfjl> list = ddxfjlService.list(new QueryWrapper<Ddxfjl>()
                .eq(StringUtils.isNotBlank(ddbh), "bh", ddbh)
                .eq("cjr", loginUser.getUserId())
                .eq(StringUtils.isNotBlank(zt), "zt", zt)
                .eq("sfsc", false)
                .orderByDesc("cjsj"));

        PageInfo pageInfo = new PageInfo(list);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation("获取用户订单")
    @GetMapping("/rpc/by/user/")
    public ResponseUtil getByUserRpc() {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        List<Ddxfjl> list = ddxfjlService.list(new QueryWrapper<Ddxfjl>().eq("cjr", loginUser.getUserId()));

        return ResponseUtil.success(list);
    }

    @ApiOperation(value = "通过订单编号获取数据")
    @GetMapping("/get/by/code/")
    public ResponseUtil getByCode(@RequestParam("code") String code) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Ddxfjl ddxfjl = ddxfjlService.getOne(new QueryWrapper<Ddxfjl>()
                .eq("cjr", loginUser.getUserId())
                .eq("bh", code));

        return ResponseUtil.success(ddxfjl);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil list(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Ddxfjl ddxfjl = ddxfjlService.getOne(new QueryWrapper<Ddxfjl>()
                .eq("cjr", loginUser.getUserId())
                .eq("id", id));

        if (ddxfjl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        return ResponseUtil.success(ddxfjl);
    }

    @ApiOperation(value = "新增")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody AddDdxfjlRequest addDdxfjlRequest) {

        checkAddRequest(addDdxfjlRequest);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Ddxfjl ddxfjl = ddxfjlService.add(addDdxfjlRequest, loginUser);

        return ResponseUtil.success(ddxfjl);
    }

    @ApiOperation(value = "订单支付接口")
    @PostMapping("/pay/")
    public ResponseUtil pay(@RequestBody Ddxfjl ddxfjl) {

        Ddxfjl dd = ddxfjlService.getById(ddxfjl.getId());
        if(dd == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        dd.setZffs(ddxfjl.getZffs());

        String result = wxPaymentService.orderInteface(ddxfjl);

        dd.setZt(OrderStatusEnum.COMPLETED.getKey());
        dd.setZfsj(new Date());
        ddxfjlService.updateById(dd);

        return ResponseUtil.success("success", result);
    }

    @ApiOperation(value = "更新状态")
    @PutMapping("/update/status/{id}")
    public ResponseUtil updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        if (OrderStatusEnum.getByKey(status) == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        Ddxfjl old = ddxfjlService.getOne(new QueryWrapper<Ddxfjl>()
                .eq("id", id)
                .eq("cjr", loginUser.getUserId()));

        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        Ddxfjl ddxfjl = ddxfjlService.updateStatus(old, status);

        return ResponseUtil.success(ddxfjl);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Ddxfjl ddxfjl = ddxfjlService.getById(id);
        if (ddxfjl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        ddxfjl.setSfsc(true);
        ddxfjl.setGxsj(new Date());

        ddxfjlService.updateById(ddxfjl);

        return ResponseUtil.success(ddxfjl);
    }

    /**
     * 校验添加参数
     */
    private void checkAddRequest(AddDdxfjlRequest addDdxfjlRequest) {

        if (addDdxfjlRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addDdxfjlRequest.getDdmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addDdxfjlRequest.getSpId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addDdxfjlRequest.getSpmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addDdxfjlRequest.getXtlx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(addDdxfjlRequest.getXflx())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addDdxfjlRequest.getZsl() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addDdxfjlRequest.getZje() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (addDdxfjlRequest.getSjzfje() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}