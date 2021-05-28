package com.itts.personTraining.controller.kssj.user;


import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kssj.Kssj;
import com.itts.personTraining.service.kssj.KsjlService;
import com.itts.personTraining.service.kssj.KssjService;
import com.itts.personTraining.vo.kssj.GetKsjlVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 考试记录 前端控制器
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-27
 */
@RestController
@RequestMapping(SystemConstant.BASE_URL + "/v1/ksjl")
@Api(tags = "考试记录门户")
public class KsjlController {

    @Autowired
    private KsjlService ksjlService;

    @Autowired
    private KssjService kssjService;

    @ApiOperation(value = "生成试卷")
    @PostMapping("/add/")
    public ResponseUtil add(@ApiParam("试卷ID") @RequestParam("sjId") Long sjId) {

        LoginUser loginUser = SystemConstant.threadLocal.get();
        if(loginUser == null){
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        Kssj kssj = kssjService.getById(sjId);
        if (kssj == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        GetKsjlVO ksjl = ksjlService.add(kssj, loginUser);

        return ResponseUtil.success(ksjl);
    }
}

