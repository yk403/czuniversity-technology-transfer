package com.itts.personTraining.controller.pk.user;

import com.itts.common.bean.LoginUser;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.xs.Xs;
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
    private XsService xsService;
    /**
     * 查询排课信息
     *
     * @return
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询排课信息")
    public ResponseUtil findPkInfo(@RequestParam(value = "skqsnyr") String skqsnyr,
                                   @RequestParam(value = "xh") String xh) {
        //TODO: 暂定,等会处理
        Long pcId = null; //xsService.getByXh(xh).getPcId();
        if(pcId==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        return ResponseUtil.success(pkService.findPkInfo(pcId));
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
