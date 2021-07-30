package com.itts.personTraining.controller.gngl.user;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.GroupFeignService;
import com.itts.personTraining.model.gngl.Gngl;
import com.itts.personTraining.model.rmdt.Rmdt;
import com.itts.personTraining.model.stgl.Stgl;
import com.itts.personTraining.service.gngl.GnglService;
import com.itts.personTraining.service.rmdt.RmdtService;
import com.itts.personTraining.service.stgl.StglService;
import com.itts.personTraining.vo.jggl.JgglVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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

    @Autowired
    private StglService stglService;

    @Autowired
    private RmdtService rmdtService;

    @Autowired
    private GroupFeignService groupFeignService;

    @GetMapping("/list/")
    @ApiOperation(value = "查询功能")
    public ResponseUtil getList(@RequestParam("jgCode") String jgCode) {

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

        List<Gngl> list = gnglService.list(new QueryWrapper<Gngl>().eq(jgglVO.getId() != null, "jg_id", jgglVO.getId()));
        //试图管理
        List<Stgl> stgls = stglService.list(new QueryWrapper<Stgl>().eq(jgglVO.getId() != null, "jg_id", jgglVO.getId()).eq("sfsc", false));
        if(!CollectionUtils.isEmpty(stgls)){
            stgls.forEach(stgl->{

                Gngl gngl = new Gngl();
                gngl.setId(stgl.getId());
                gngl.setJgId(stgl.getJgId());
                gngl.setGnmc(stgl.getMc());
                gngl.setGnjs(stgl.getJj());
                gngl.setSfsy(stgl.getSfxs());
                list.add(gngl);
            });
        }

        //热门动态
        List<Rmdt> rmdts = rmdtService.list(new QueryWrapper<Rmdt>().eq(jgglVO.getId() != null, "jg_id", jgglVO.getId()).eq("sfsc",false));
        if(CollectionUtils.isEmpty(rmdts)){
            rmdts.forEach(rmdt->{

                Gngl gngl = new Gngl();
                gngl.setId(rmdt.getId());
                gngl.setJgId(rmdt.getJgId());
                gngl.setGnmc(rmdt.getMc());
                gngl.setGnjs(rmdt.getJj());
                gngl.setSfsy(rmdt.getSfxs());
                list.add(gngl);
            });
        }

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

