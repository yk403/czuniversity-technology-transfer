package com.itts.personTraining.controller.lmgl.user;


import com.alibaba.fastjson.TypeReference;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.GroupFeignService;
import com.itts.personTraining.model.lmgl.Lmgl;
import com.itts.personTraining.service.lmgl.LmglService;
import com.itts.personTraining.vo.jggl.JgglVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-13
 */
@RestController
@RequestMapping(BASE_URL +"/v1/lmgl")
@Api(tags = "栏目门户")
public class LmglController {

    @Autowired
    private LmglService lmglService;

    @Autowired
    private GroupFeignService groupFeignService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询")
    public ResponseUtil getList(@RequestParam(value = "jgCode") String jgCode) {

        if(StringUtils.isBlank(jgCode)){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        ResponseUtil response = groupFeignService.getByCode(jgCode);
        if(response == null || response.getErrCode().intValue() != 0){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        JgglVO jggl = response.conversionData(new TypeReference<JgglVO>() {
        });

        return ResponseUtil.success(lmglService.findList(jggl.getId()));
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
        Lmgl lmgl = lmglService.get(id);
        if(lmgl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(lmgl);
    }

}

