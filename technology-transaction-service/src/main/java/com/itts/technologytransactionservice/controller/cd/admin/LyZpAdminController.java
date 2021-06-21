package com.itts.technologytransactionservice.controller.cd.admin;


import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.LyZp;
import com.itts.technologytransactionservice.service.cd.LyZpAdminService;
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
 * @since 2021-05-18
 */
@RestController
@RequestMapping(ADMIN_BASE_URL+"/v1/LyZp")
@Api(value = "LyZpAdminController", tags = "双创路演展品后台管理")
public class LyZpAdminController {
    @Autowired
    private LyZpAdminService lyZpAdminService;
//    *
//     * 获取列表

    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
//return null;
        return ResponseUtil.success(lyZpAdminService.findLyZpBack(params));
    }
//    *
//     * 根据ID查询
//     * @param id
//     * @return

    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Long id) {
        return ResponseUtil.success(lyZpAdminService.getById(id));
    }
//    *
//     * 保存

    @PostMapping("/save")
    @ApiOperation(value ="新增")
    public ResponseUtil save(@RequestBody LyZp lyZp) {
        if (!lyZpAdminService.saveZp(lyZp)) {
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
    @ApiOperation(value = "更新报名")
    public ResponseUtil update(@RequestBody LyZp lyZp) throws WebException {
        Long id = lyZp.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (lyZpAdminService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
        if (!lyZpAdminService.updateById(lyZp)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新课程成功!");

    }
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        LyZp old = lyZpAdminService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        old.setIsDelete(true);
        old.setGxsj(new Date());

        lyZpAdminService.updateById(old);

        return ResponseUtil.success(old);
    }
    /**
     * 发布审核需求(1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @RequestMapping("/audit")
    public ResponseUtil audit(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
        if (fbshzt != 2 && fbshzt != 3 && fbshzt != 4) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!lyZpAdminService.audit(params, fbshzt)) {
            throw new WebException(AUDIT_MSG_ISEMPTY);
        }
        return ResponseUtil.success("审核需求完成!");
    }
}

