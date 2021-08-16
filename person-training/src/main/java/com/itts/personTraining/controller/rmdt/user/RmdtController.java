package com.itts.personTraining.controller.rmdt.user;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.GroupFeignService;
import com.itts.personTraining.model.rmdt.Rmdt;
import com.itts.personTraining.service.rmdt.RmdtService;
import com.itts.personTraining.vo.jggl.JgglVO;
import com.itts.personTraining.vo.rmdt.RmVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-26
 */
@RestController
@RequestMapping(BASE_URL +"/v1/rmdt")
@Api(value = "RmdtController",tags="热门动态")
public class RmdtController {

    @Resource
    private RmdtService rmdtService;
    @Autowired
    private GroupFeignService groupFeignService;

    @GetMapping("/list/")
    @ApiOperation(value = "查询功能")
    public ResponseUtil getList(@RequestParam("jgCode") String jgCode){

        if(StringUtils.isBlank(jgCode)){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        ResponseUtil response = groupFeignService.getByCode(jgCode);
        if(response == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        if(response.getErrCode().intValue() != 0){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        JgglVO jgglVO = response.conversionData(new TypeReference<JgglVO>(){});
        if(jgglVO == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        List<Rmdt> list = rmdtService.list(new QueryWrapper<Rmdt>().eq(jgglVO.getId() != null, "jg_id", jgglVO.getId())
        .orderByAsc("px"));

        List<RmVO> collect = list.stream().map(rmdt -> {
            RmVO rmVO = new RmVO();
            BeanUtils.copyProperties(rmdt, rmVO);
            if (StringUtils.equals(rmdt.getMc(), "新闻")) {
                rmVO.setComponentName("PopNews");
            }
            if (StringUtils.equals(rmdt.getMc(), "公告")) {
                rmVO.setComponentName("PopNotice");
            }
            return rmVO;
        }).collect(Collectors.toList());

        return ResponseUtil.success(collect);
    }
}

