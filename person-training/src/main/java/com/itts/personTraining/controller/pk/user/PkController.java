package com.itts.personTraining.controller.pk.user;

import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.service.kc.KcService;
import com.itts.personTraining.service.pk.PkService;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.threadLocal;
import static com.itts.common.enums.ErrorCodeEnum.GET_THREADLOCAL_ERROR;

@RestController
@RequestMapping(BASE_URL + "/v1/pk")
@Api(value = "PkController", tags = "排课前台")
public class PkController {
    @Autowired
    private PkService pkService;
    @Autowired
    private KcService kcService;

    /**
     * 根据用户id查询课程表
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据用户id查询列表")
    public ResponseUtil findKcByYh(@RequestParam(value = "pcId",required = false) Long pcId) {
        return ResponseUtil.success(kcService.findByPcId(pcId));
    }

    /**
     * 根据用户id查询批次集合(前)(老师)
     *
     * @return
     */
    @GetMapping("/getPcsByYhId")
    @ApiOperation(value = "根据用户id查询批次集合(前)")
    public ResponseUtil getPcsByYhId() {
        return ResponseUtil.success(pkService.getPcsByYhId());
    }
}
