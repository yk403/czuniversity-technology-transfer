package com.itts.personTraining.controller.ggtz.user;


import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.ggtz.Ggtz;
import com.itts.personTraining.service.ggtz.GgtzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.UPDATE_FAIL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-12
 */
@RestController
@RequestMapping(BASE_URL +"/v1/ggtz")
@Api(value = "GgtzController", tags = "公告通知门户")
public class GgtzController {

    @Resource
    private GgtzService ggtzService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询新闻")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "jgId",required = false) Long jgId,
                                @RequestParam(value = "zt",required = false) String zt,
                                @RequestParam(value = "lx",required = false) String lx,
                                @RequestParam(value = "tzbt",required = false)String tzbt) throws WebException {
        return ResponseUtil.success(ggtzService.findByPage(pageNum, pageSize, jgId, zt, lx,tzbt));
    }

    /**
     * 查询
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询")
    public ResponseUtil get(@PathVariable("id") Long id) throws WebException{
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Ggtz ggtz = ggtzService.get(id);
        if(ggtz == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(ggtz);
    }

}

