package com.itts.userservice.controller.cz;


import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.dto.CzDTO;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.service.cz.CzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 操作表 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-19
 */
@Api(tags = "操作管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/cz")
public class CzController {

    @Resource
    private CzService czService;


    /**
     * 查询当前菜单的操作
     * @param id
     * @param cdid
     * @return
     */
    @GetMapping("/czlist/{id}/{cdid}")
    @ApiOperation(value = "获取操作列表")
    public ResponseUtil findcz(@PathVariable("id") Long id,@PathVariable("cdid") Long cdid){
        List<CzDTO> cz = czService.findCz(id, cdid);
        return ResponseUtil.success(cz);
    }

    /**
     * 获取列表
     *
     * @param pageNum pageSize
     * @author fl
     *
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取列表")
    public ResponseUtil find(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageInfo<Cz> byPage = czService.findByPage(pageNum, pageSize);
        return ResponseUtil.success(byPage);
    }

    /**
     * 新增
     *
     * @author FULI
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Cz tCz) throws WebException {
        //检查参数是否合法
        checkPequest(tCz);
        //检查数据库中是否存在要更新的数据
        Cz add = czService.add(tCz);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     *
     * @author FULI
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Cz tCz) throws WebException {
        Long id = tCz.getId();
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        Cz tCz1 = czService.get(id);
        if (tCz1 == null) {
            throw new WebException((ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR));
        }
        checkPequest(tCz);
        //浅拷贝，更新的数据覆盖已存数据,并过滤指定字段
        BeanUtils.copyProperties(tCz, tCz1, "id", "cjsj", "cjr");
        czService.update(tCz1);
        return ResponseUtil.success(tCz1);
    }

    /**
     * 删除
     *
     * @author FULI
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Cz tCz = czService.get(id);
        if (tCz == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //设置删除状态，更新删除时间
        tCz.setSfsc(false);
        tCz.setGxsj(new Date());
        czService.update(tCz);
        return ResponseUtil.success();
    }

    /**
     * 校验参数是否合法
     */
    private void checkPequest(Cz tCz) throws WebException {
        //如果参数为空，抛出异常
        if (tCz == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }


}

