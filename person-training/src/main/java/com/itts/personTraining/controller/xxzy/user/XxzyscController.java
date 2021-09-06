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
import com.itts.personTraining.model.xxzy.Xxzy;
import com.itts.personTraining.model.xxzy.Xxzysc;
import com.itts.personTraining.service.xxzy.XxzyService;
import com.itts.personTraining.service.xxzy.XxzyscService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private XxzyService xxzyService;

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

            List<Xxzy> xxzys = JSONUtil.toList(jsonArry, Xxzy.class);

            return ResponseUtil.success(xxzys);
        }

        List<Xxzy> list = cache2Redis(loginUser.getUserId());

        return ResponseUtil.success(list);
    }

    @ApiOperation(value = "获取收藏的学习资源")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "一级分类") @RequestParam(value = "firstCategory", required = false) String firstCategory,
                             @ApiParam(value = "二级分类") @RequestParam(value = "secondCategory", required = false) String secondCategory,
                             @ApiParam(value = "资源类型: video - 视频; textbook - 教材; courseware - 课件") @RequestParam(value = "category", required = false) String category,
                             @ApiParam(value = "资源方向: knowledge - 知识; skill - 技能; ability - 能力") @RequestParam(value = "direction", required = false) String direction,
                             @ApiParam(value = "收藏学习资源ID") @RequestParam(value = "id", required = false) Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        PageInfo<Xxzy> pageInfo = xxzyscService.findScByPage(pageNum, pageSize, loginUser.getUserId(),
                firstCategory, secondCategory, category, direction, id);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "添加收藏")
    @PostMapping("/add/")
    public ResponseUtil add(@ApiParam("学习资源ID") @RequestParam("xxzyId") Long xxzyId) {

        if (xxzyId == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Xxzy xxzy = xxzyService.getById(xxzyId);
        if (xxzy == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        Xxzysc data = xxzyscService.add(xxzy, loginUser.getUserId());

        cache2Redis(loginUser.getUserId());

        return ResponseUtil.success(data);
    }

    @ApiOperation(value = "取消收藏")
    @DeleteMapping("/delete/{xxzyId}")
    public ResponseUtil delete(@PathVariable("xxzyId") Long xxzyId) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if (loginUser == null) {

            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        xxzyscService.remove(new QueryWrapper<Xxzysc>().eq("xxzy_id", xxzyId).eq("yh_id", loginUser.getUserId()));

        cache2Redis(loginUser.getUserId());

        return ResponseUtil.success();
    }

    /**
     * 查询数据库数据缓存到redis
     */
    private List<Xxzy> cache2Redis(Long userId) {

        PageHelper.startPage(1, 10);
        List<Xxzysc> list = xxzyscService.list(new QueryWrapper<Xxzysc>().eq("yh_id", userId).orderByDesc("cjsj"));

        if (!CollectionUtils.isEmpty(list)) {

            List<Long> xxzyIds = list.stream().map(Xxzysc::getXxzyId).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(xxzyIds)){

                List<Xxzy> xxzys = xxzyService.list(new QueryWrapper<Xxzy>().eq("sfsc",false).in("id", xxzyIds));

                redisTemplate.opsForValue().set(RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_PREFIX + userId,
                        JSONUtil.toJsonStr(xxzys), RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_EXPIRE, TimeUnit.DAYS);

                return xxzys;
            }
        }

        redisTemplate.delete(RedisConstant.USER_LEARNING_RESOURCES_FAVORITES_PREFIX + userId);

        return null;
    }
}

