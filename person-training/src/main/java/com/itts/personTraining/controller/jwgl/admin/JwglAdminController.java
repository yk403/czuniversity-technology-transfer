package com.itts.personTraining.controller.jwgl.admin;

import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.dto.JwglDTO;
import com.itts.personTraining.model.kcXs.KcXs;
import com.itts.personTraining.model.xs.Xs;
import com.itts.personTraining.request.AddJwglRequset;
import com.itts.personTraining.service.kcXs.KcXsService;
import com.itts.personTraining.service.xs.XsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/v1/jwgl")
@Api(value = "JwglAdminController", tags = "教务管理")
public class JwglAdminController {
    @Autowired
    private XsService xsService;

    /*@Autowired
    private KcXsService kcXsService;*/

    @GetMapping("/list/")
    @ApiOperation(value = "教务管理列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "string", required = false) String string,
                                @RequestParam(value = "pcId", required = false) Long pcId,
                                @RequestParam(value = "yx", required = false) String yx){
        PageInfo<JwglDTO> jwglByPage = xsService.findJwglByPage(pageNum, pageSize, string, yx ,pcId);
        return ResponseUtil.success(jwglByPage);
    }

    /**
     * 新增学生课程关联
     * @param addJwglRequset
     * @return
     */
    @PostMapping("/add/")
    @ApiOperation(value ="新增")
    public ResponseUtil add(@RequestBody AddJwglRequset addJwglRequset){
        checkRequest(addJwglRequset);
        List<Long> kcIdList = addJwglRequset.getKcIdList();
        KcXs kcXs = new KcXs();
        kcXs.setXsId(addJwglRequset.getId());
        for (int i = 0; i < kcIdList.size(); i++) {

            kcXs.setKcId(kcIdList.get(i));
            //kcXsService.insert(kcXs);
        }
        return ResponseUtil.success(addJwglRequset);
    }
    @PutMapping("/update/")
    @ApiOperation(value="更新")
    public ResponseUtil update(@RequestBody AddJwglRequset addJwglRequset){
        return ResponseUtil.success();
    }
    /**
     * 校验参数是否合法
     */
    private void checkRequest(AddJwglRequset addJwglRequset) throws WebException {
        //如果参数为空，抛出异常
        if (addJwglRequset == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (addJwglRequset.getId() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (addJwglRequset.getKcIdList() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}
