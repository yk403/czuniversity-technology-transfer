package com.itts.personTraining.controller.xxzy.user;

import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.request.xxzy.BuyXxzyRequest;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.vo.xxzy.GetXxzyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
                             @ApiParam(value = "课程ID") @RequestParam(value = "courseId", required = false) Long courseId,
                             @ApiParam(value = "查询条件") @RequestParam(value = "condition", required = false) String condition,
                             HttpServletRequest request) {

        PageInfo<GetXxzyVO> result = xxzyService.listVO(pageNum, pageSize, type, firstCategory, secondCategory, courseId, condition, request.getHeader("token"));

        return ResponseUtil.success(result);

    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Xxzy xxzy = xxzyService.getById(id);

        if (xxzy == null || xxzy.getSfsc() || !xxzy.getSfsj()) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetXxzyVO vo = new GetXxzyVO();
        BeanUtils.copyProperties(xxzy, vo);

        xxzy.setLll(xxzy.getLll().intValue() + 1);

        xxzyService.updateById(xxzy);

        return ResponseUtil.success(vo);
    }

    @ApiOperation(value = "购买学习资源")
    @PostMapping("/buy/")
    public ResponseUtil buy(@RequestBody BuyXxzyRequest buyXxzyRequest, HttpServletRequest request) {

        checkBuyRequest(buyXxzyRequest);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if(loginUser == null){
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        ResponseUtil result = xxzyService.buy(buyXxzyRequest, request.getHeader("token"));

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