package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.exception.ServiceException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.config.MqConfig;
import com.itts.technologytransactionservice.model.JsMsg;
import com.itts.technologytransactionservice.model.JsMsgDTO;
import com.itts.technologytransactionservice.service.JsMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.threadLocal;
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
@RequestMapping(BASE_URL + "/v1/jsMsg")
@Api(tags = "技术留言")
public class JsMsgController {
    @Resource
    private JsMsgService jsMsgService;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/list/")
    @ApiOperation(value = "留言列表")
    public ResponseUtil getList(@RequestParam(value = "jjrId")Long jjrId){
        List<JsMsg> jsMsgs = jsMsgService.list(new QueryWrapper<JsMsg>().eq("jjr_id", jjrId).eq("yh_id",getUserId()));
        return ResponseUtil.success(jsMsgs);
    }
    @PostMapping("/add/")
    @ApiOperation(value = "留言")
    public ResponseUtil update(@RequestBody JsMsg jsMsg){
        jsMsg.setYhId(getUserId());
        jsMsg.setFjjgId(getFjjgId());
        jsMsg.setLysj(new Date());
        jsMsg.setCjsj(new Date());
        rabbitTemplate.convertAndSend(MqConfig.eventExchange,MqConfig.routingKey,jsMsg);
        return ResponseUtil.success();
    }
    public Long getUserId() {
        LoginUser loginUser = threadLocal.get();
        Long userId;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            throw new ServiceException(GET_THREADLOCAL_ERROR);
        }
        return userId;
    }

    private Long getFjjgId(){
        LoginUser loginUser = threadLocal.get();
        Long fjjgId = null;
        if (loginUser != null) {
            fjjgId = loginUser.getFjjgId();
        }
        return fjjgId;
    }
}

