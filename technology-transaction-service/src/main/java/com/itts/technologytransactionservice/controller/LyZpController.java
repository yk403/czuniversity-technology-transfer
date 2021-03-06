package com.itts.technologytransactionservice.controller;


import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.LyZp;
import com.itts.technologytransactionservice.service.LyZpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.UNCHECK_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;
import static com.itts.common.enums.ErrorCodeEnum.UPDATE_FAIL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
@RestController
@RequestMapping(BASE_URL+"/v1/LyZp")
@Api(value = "LyZpController", tags = "双创路演展品门户端")
public class LyZpController {
    @Autowired
    private LyZpService lyZpService;
//    *
//     * 获取列表

    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
//return null;
        return ResponseUtil.success(lyZpService.findLyZpFront(params));
    }
    //    *
//     * 获取列表

    @ApiOperation(value = "获取列表")
    @PostMapping("/listUser")
    public ResponseUtil findUser(@RequestBody Map<String, Object> params) {
//return null;
        return ResponseUtil.success(lyZpService.findLyZpFrontUser(params));
    }
//    *
//     * 根据ID查询
//     * @param id
//     * @return

    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Long id) {
        return ResponseUtil.success(lyZpService.getById(id));
    }
//    *
//     * 保存

    @PostMapping("/save")
    @ApiOperation(value ="新增")
    public ResponseUtil save(@RequestBody LyZp lyZp) {
        if (!lyZpService.saveZp(lyZp)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("报名成功");
    }
    /*    *
     * 更新课程
     *
     * @param lyBm
     * @return
     * @throws WebException*/

    @PutMapping("/update")
    @ApiOperation(value = "更新展品")
    public ResponseUtil update(@RequestBody LyZp lyZp) throws WebException {
        Long id = lyZp.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (lyZpService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
        if (!lyZpService.updateById(lyZp)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新展品成功!");

    }
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        LyZp old = lyZpService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        old.setIsDelete(true);
        old.setGxsj(new Date());

        lyZpService.updateById(old);

        return ResponseUtil.success(old);
    }
    /**
     * 个人发布审核成果申请(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @PutMapping("/audit")
    public ResponseUtil auditCg(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
/*        if (fbshzt != 1) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }*/
        return ResponseUtil.success("申请审核成功", lyZpService.audit(params, fbshzt));
    }
}

