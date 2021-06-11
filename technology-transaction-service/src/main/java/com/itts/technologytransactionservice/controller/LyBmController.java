package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.service.LyBmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.UNCHECK_BASE_URL;
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
@RequestMapping(BASE_URL+"/v1/LyBm")
@Api(value = "LyBmController", tags = "双创路演报名门户端")
public class LyBmController {
    @Autowired
    private LyBmService lyBmService;
    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(lyBmService.findLyBmFront(params));
    }
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Long id) {
        return ResponseUtil.success(lyBmService.getById(id));
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value ="新增")
    public ResponseUtil save(@RequestBody LyBm lyBm) {
        if (!lyBmService.saveBm(lyBm)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("报名成功");
    }
    /**
     * 更新课程
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
        if (lyBmService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
       if (!lyBmService.updateLyBm(lyBm)) {
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

        LyBm old = lyBmService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        old.setIsDelete(true);
        old.setGxsj(new Date());
        old.setUserId(loginUser.getUserId());

        lyBmService.updateById(old);

        return ResponseUtil.success(old);
    }

}

