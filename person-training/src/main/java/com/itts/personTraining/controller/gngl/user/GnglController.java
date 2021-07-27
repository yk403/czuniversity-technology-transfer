package com.itts.personTraining.controller.gngl.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.gngl.Gngl;
import com.itts.personTraining.service.gngl.GnglService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-21
 */
@RestController
@RequestMapping(BASE_URL + "/v1/gngl")
@Api(tags = "功能管理 - 门户")
public class GnglController {

    @Resource
    private GnglService gnglService;

    @GetMapping("/list/")
    @ApiOperation(value = "查询功能")
    public ResponseUtil getList(@RequestParam("jgId") Long jgId) {

        List<Gngl> list = gnglService.list(new QueryWrapper<Gngl>().eq(jgId != null, "jg_id", jgId));

        return ResponseUtil.success(list);
    }

    /**
     * 查询
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询")
    public ResponseUtil get(@PathVariable("id") Long id) throws WebException {
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Gngl gngl = gnglService.get(id);
        if (gngl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(gngl);
    }
}

