package com.itts.personTraining.controller.xxzy.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.ddxfjl.PayDdxfjlRequest;
import com.itts.personTraining.request.xxzy.BuyXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/8
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/xxzy")
@Api(tags = "学习资源 - 门户")
public class XxzyController {

    @Autowired
    private XxzyService xxzyService;

    @ApiOperation(value = "列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "资源类型：in-内部资源；out-外部资源") @RequestParam(value = "type") String type,
                             @ApiParam(value = "一级分类") @RequestParam(value = "firstCategory", required = false) String firstCategory,
                             @ApiParam(value = "二级分类") @RequestParam(value = "secondCategory", required = false) String secondCategory,
                             @ApiParam(value = "资源类型: video - 视频; textbook - 教材; courseware - 课件") @RequestParam(value = "category", required = false) String category,
                             @ApiParam(value = "资源方向: knowledge - 知识; skill - 技能; ability - 能力") @RequestParam(value = "direction", required = false) String direction,
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) Long courseId,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition) {

        PageInfo<GetXxzyVO> result = xxzyService.listVO(pageNum, pageSize, type, firstCategory, secondCategory, category, direction, courseId, condition);

        return ResponseUtil.success(result);

    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        GetXxzyVO vo = xxzyService.get(id);

        if (vo == null || vo.getSfsc() || !vo.getSfsj()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        Xxzy xxzy = xxzyService.getById(id);
        xxzy.setLll(xxzy.getLll().intValue() + 1);

        xxzyService.updateById(xxzy);

        return ResponseUtil.success(vo);
    }

    @ApiOperation(value = "购买学习资源")
    @PostMapping("/buy/")
    public ResponseUtil buy(@RequestBody BuyXxzyRequest buyXxzyRequest) {

        checkBuyRequest(buyXxzyRequest);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Xxzy xxzy = xxzyService.getOne(new QueryWrapper<Xxzy>()
                .eq("id", buyXxzyRequest.getSpId())
                .eq("sfsc", false)
                .eq("sfsj", true));

        if (xxzy == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        ResponseUtil result = xxzyService.buy(buyXxzyRequest);

        return result;
    }

    @ApiOperation(value = "支付金额")
    @PostMapping("/pay/")
    public ResponseUtil pay(@RequestBody PayDdxfjlRequest payDdxfjlRequest) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        ResponseUtil result = xxzyService.pay(payDdxfjlRequest);

        return result;
    }

    /**
     * 校验购买参数是否合法
     */
    private void checkBuyRequest(BuyXxzyRequest buyXxzyRequest) {

        if (buyXxzyRequest == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (buyXxzyRequest.getSpId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(buyXxzyRequest.getSpmc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(buyXxzyRequest.getZffs())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (buyXxzyRequest.getZsl() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (buyXxzyRequest.getZje() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (buyXxzyRequest.getSjzfje() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(buyXxzyRequest.getLxdh())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}