package com.itts.technologytransactionservice.controller.cd.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.JsJjr;
import com.itts.technologytransactionservice.model.JsMsg;
import com.itts.technologytransactionservice.model.JsMsgDTO;
import com.itts.technologytransactionservice.service.JsJjrService;
import com.itts.technologytransactionservice.service.JsMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.*;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
@RestController
@RequestMapping(ADMIN_BASE_URL + "/v1/jsMsg")
@Api(tags = "技术留言后台管理")
public class JsMsgAdminController {


    @Resource
    private JsMsgService jsMsgService;

    @GetMapping("/list/")
    @ApiOperation(value = "留言列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "yhm",required = false)String yhm){
        PageInfo<JsMsgDTO> page = jsMsgService.findPage(pageNum, pageSize,yhm);
        return ResponseUtil.success(page);
    }
    @PutMapping("/update/")
    @ApiOperation(value = "回复")
    public ResponseUtil update(@RequestBody JsMsg jsMsg){
        JsMsg byId = jsMsgService.getById(jsMsg.getId());
        if(byId == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        BeanUtils.copyProperties(jsMsg,byId);
        byId.setLyzt(true);
        byId.setGxsj(new Date());
        byId.setHfsj(new Date());
        return ResponseUtil.success(jsMsgService.updateById(byId));
    }
}

