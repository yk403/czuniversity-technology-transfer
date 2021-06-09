package com.itts.personTraining.controller.xxzy.user;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.RedisConstant;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xxzy.Xxzysc;
import com.itts.personTraining.service.xxzy.XxzyscService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 学习资源收藏 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-06-09
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/xxzysc")
@Api(tags = "学习资源收藏")
public class XxzyscController {

    @Autowired
    private XxzyscService xxzyscService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "综合信息收藏列表展示（部分）")
    @GetMapping("/list/index/")
    public ResponseUtil list() {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Object redisData = redisTemplate.opsForValue().get(RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_PREFIX + loginUser.getUserId());

        if (redisData != null) {

            String redisStr = redisData.toString();
            JSONArray jsonArry = JSONUtil.parseArray(redisStr);

            List<Xxzysc> xxzycss = JSONUtil.toList(jsonArry, Xxzysc.class);

            return ResponseUtil.success(xxzycss);
        }

        PageHelper.startPage(1, 10);
        List<Xxzysc> list = xxzyscService.list(new QueryWrapper<Xxzysc>().eq("yh_id", loginUser.getUserId()));

        if (!CollectionUtils.isEmpty(list)) {

            redisTemplate.opsForValue().set(RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_PREFIX + loginUser.getUserId(),
                    JSONUtil.toJsonStr(list), RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_EXPIRE, TimeUnit.DAYS);
        }

        return ResponseUtil.success(list);
    }

}

