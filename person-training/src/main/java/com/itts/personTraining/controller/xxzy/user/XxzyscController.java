package com.itts.personTraining.controller.xxzy.user;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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
    public ResponseUtil listByCache() {

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

        List<Xxzysc> list = cache2Redis(loginUser.getUserId());

        return ResponseUtil.success(list);
    }

    @ApiOperation(value = "获取收藏的学习资源")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "课程名称") @RequestParam(value = "courseName", required = false) String courseName,
                             @ApiParam(value = "资源名称") @RequestParam(value = "name", required = false) String name) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        PageHelper.startPage(pageNum, pageSize);

        List<Xxzysc> list = xxzyscService.list(new QueryWrapper<Xxzysc>().
                eq("yh_id", loginUser.getUserId())
                .like(StringUtils.isNotBlank(courseName), "kcmc", courseName.trim())
                .like(StringUtils.isNotBlank(name), "xxzy_mc", name.trim())
                .orderByDesc("cjsj"));

        PageInfo pageInfo = new PageInfo(list);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/add/")
    public ResponseUtil add(@RequestBody Xxzysc xxzysc) {

        checkRequest(xxzysc);

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Xxzysc data = xxzyscService.add(xxzysc, loginUser.getUserId());

        cache2Redis(loginUser.getUserId());

        return ResponseUtil.success(data);
    }

    @ApiOperation(value = "取消收藏")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id){

        xxzyscService.delete(id);

        return ResponseUtil.success();
    }

    /**
     * 查询数据库数据缓存到redis
     */
    private List<Xxzysc> cache2Redis(Long userId) {

        PageHelper.startPage(1, 10);
        List<Xxzysc> list = xxzyscService.list(new QueryWrapper<Xxzysc>().eq("yh_id", userId).orderByDesc("cjsj"));

        if (!CollectionUtils.isEmpty(list)) {

            redisTemplate.opsForValue().set(RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_PREFIX + userId,
                    JSONUtil.toJsonStr(list), RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_EXPIRE, TimeUnit.DAYS);
        }

        return list;
    }

    /**
     * 校验请求参数
     */
    private void checkRequest(Xxzysc xxzysc) {

        if (xxzysc == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (xxzysc.getXxzyId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (StringUtils.isBlank(xxzysc.getXxzyMc())) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

}
