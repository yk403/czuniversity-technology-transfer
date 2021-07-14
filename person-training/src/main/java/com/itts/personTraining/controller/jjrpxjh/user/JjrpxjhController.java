package com.itts.personTraining.controller.jjrpxjh.user;

import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.JjrbmInfo;
import com.itts.personTraining.dto.JjrpxjhDTO;
import com.itts.personTraining.model.jjrpxjh.Jjrpxjh;
import com.itts.personTraining.service.jjrpxjh.JjrpxjhService;
import com.itts.personTraining.vo.jjrpxjh.GetJjrpxjhVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.itts.common.enums.ErrorCodeEnum.SIGN_UP_FAIL;

/**
 * @Description：
 * @Author：lym
 * @Date: 2021/6/1
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/jjrpxjh")
@Api(tags = "经纪人培训计划 - 门户")
public class JjrpxjhController {

    @Autowired
    private JjrpxjhService jjrpxjhService;

    @ApiOperation(value = "获取经纪人培训计划")
    @GetMapping("/getJjrpxjh/")
    public ResponseUtil getJjrpxjh() {
        JjrpxjhDTO pageInfo = jjrpxjhService.getJjrpxjh();
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

    @ApiOperation(value = "培训报名")
    @PostMapping("/sign/up/")
    public ResponseUtil saveJjrInfo(@RequestBody JjrbmInfo jjrbmInfo) throws WebException {
        if (!jjrpxjhService.saveJjrInfo(jjrbmInfo)) {
            throw new WebException(SIGN_UP_FAIL);
        }
        return ResponseUtil.success("培训报名成功!");
    }
}