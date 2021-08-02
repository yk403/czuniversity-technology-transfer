package com.itts.personTraining.controller.stgl.user;


import com.alibaba.fastjson.TypeReference;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.feign.userservice.GroupFeignService;
import com.itts.personTraining.model.stgl.Stgl;
import com.itts.personTraining.service.stgl.StglService;
import com.itts.personTraining.vo.jggl.JgglVO;
import com.itts.personTraining.vo.stgl.StVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
@RequestMapping(BASE_URL +"/v1/stgl")
@Api(value = "StglController", tags = "视图门户")
public class StglController {

    @Autowired
    private StglService stglService;
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

        List<Stgl> result = stglService.findList(jggl.getId());

        List<StVO> voList = result.stream().map(st -> {

            StVO vo = new StVO();

            BeanUtils.copyProperties(st, vo);

            if (StringUtils.equals(st.getMc(), "需求大厅")) {
                vo.setComponentName("Demand");
            }

            if (StringUtils.equals(st.getMc(), "技术成果")) {
                vo.setComponentName("Achievement");
            }

            if (StringUtils.equals(st.getMc(), "教学资源")) {
                vo.setComponentName("Resource");
            }

            if (StringUtils.equals(st.getMc(), "师资团队")) {
                vo.setComponentName("Teacher");
            }

            return vo;
        }).collect(Collectors.toList());

        return ResponseUtil.success(voList);
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
        Stgl stgl = stglService.get(id);
        if(stgl == null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(stgl);
    }

}

