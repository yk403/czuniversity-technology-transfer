package com.itts.personTraining.controller.jjrpxjh.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.request.jjrpxjh.SignUpJjrpxjhRequest;
import com.itts.personTraining.service.jjrpxjh.JjrpxjhService;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/jjrpxjh/v1")
@Api(tags = "经纪人培训计划 - 门户")
public class JjrpxjhController {

    @Autowired
    private JjrpxjhService jjrpxjhService;

    @ApiOperation(value = "获取列表")
    @GetMapping("/list/")
    public ResponseUtil list(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                             @ApiParam(value = "名称") @RequestParam(value = "mc", required = false) String mc) {

        PageHelper.startPage(pageNum, pageSize);

        List<Jjrpxjh> list = jjrpxjhService.list(new QueryWrapper<Jjrpxjh>()
                .eq("sfsc", false).eq("sfsj", true)
                .like(StringUtils.isNotBlank(mc), "pxjhmc", mc)
                .orderByDesc("cjsj"));

        PageInfo pageInfo = new PageInfo(list);

        return ResponseUtil.success(pageInfo);
    }

    @ApiOperation(value = "获取详情")
    @GetMapping("/get/{id}")
    public ResponseUtil get(@PathVariable("id") Long id) {

        Jjrpxjh jjrpxjh = jjrpxjhService.getById(id);
        if (jjrpxjh == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetJjrpxjhVO jjrpxjhVO = jjrpxjhService.get(jjrpxjh);

        return ResponseUtil.success(jjrpxjhVO);
    }

    @ApiOperation(value = "报名")
    @PostMapping("/sign/up/")
    public ResponseUtil signUp(@RequestBody SignUpJjrpxjhRequest signUpJjrpxjhRequest){
        return null;
    }
}