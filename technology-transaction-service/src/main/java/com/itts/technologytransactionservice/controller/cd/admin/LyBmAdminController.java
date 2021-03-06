package com.itts.technologytransactionservice.controller.cd.admin;


import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.cd.LyBmAdminService;
import com.itts.technologytransactionservice.service.cd.LyHdAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yukai
 * @since 2021-05-17
 */
@RestController
@RequestMapping(ADMIN_BASE_URL+"/v1/LyBm")
@Api(value = "LyBmAdminController", tags = "双创路演报名后台管理")
public class LyBmAdminController {
    @Autowired
    private LyBmAdminService lyBmAdminService;
    @Autowired
    private LyHdAdminService lyHdAdminService;
    @Autowired
    private JsXtxxService jsXtxxService;
    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(lyBmAdminService.findLyBmBack(params));
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Long id) {
        return ResponseUtil.success(lyBmAdminService.getById(id));
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value ="新增")
    public ResponseUtil save(@RequestBody LyBm lyBm) {
        if (!lyBmAdminService.saveBm(lyBm)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("报名成功");
    }
    /**
     * 更新报名
     *
     * @param lyBm
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新报名")
    public ResponseUtil update(@RequestBody LyBm lyBm) throws WebException {
        Long id = lyBm.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (lyBmAdminService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
       if (!lyBmAdminService.updateLyBm(lyBm)) {
            throw new WebException(UPDATE_FAIL);
        }
        if(lyBm.getShzt().equals(2)){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),lyBm.getUserId()==null?null:lyBm.getUserId().longValue(),5,0,lyHdAdminService.getById(lyBm.getHdId()).getHdmc());
        }
        if(lyBm.getShzt().equals(1)){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),lyBm.getUserId()==null?null:lyBm.getUserId().longValue(),5,1,lyHdAdminService.getById(lyBm.getHdId()).getHdmc());
        }
        return ResponseUtil.success("报名审核成功!");

    }
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        LyBm old = lyBmAdminService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        old.setIsDelete(true);
        old.setGxsj(new Date());
        //old.setUserId(loginUser.getUserId());

        lyBmAdminService.updateById(old);

        return ResponseUtil.success(old);
    }

}

