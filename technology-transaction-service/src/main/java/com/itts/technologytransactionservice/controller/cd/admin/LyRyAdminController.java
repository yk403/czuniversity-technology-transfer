package com.itts.technologytransactionservice.controller.cd.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.mapper.LyRyMapper;
import com.itts.technologytransactionservice.model.LyRy;
import com.itts.technologytransactionservice.service.LyRyService;
import com.itts.technologytransactionservice.service.cd.LyRyAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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
@RequestMapping(ADMIN_BASE_URL+"/v1/LyRy")
@Api(value = "LyRyAdminController", tags = "双创路演人员后台管理")
public class LyRyAdminController {
    @Autowired
    private LyRyAdminService lyRyAdminService;
    @Resource
    private LyRyMapper lyRyMapper;
//    *
//     * 获取列表

    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil find(@RequestBody Map<String, Object> params) {
//return null;
        return ResponseUtil.success(lyRyAdminService.findLyRyBack(params));
    }
//    *
//     * 根据ID查询
//     * @param id
//     * @return

    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Long id) {
        return ResponseUtil.success(lyRyAdminService.getById(id));
    }
//    *
//     * 保存

    @PostMapping("/save")
    @ApiOperation(value ="新增")
    public ResponseUtil save(@RequestBody LyRy lyRy) {
        QueryWrapper<LyRy> lyRyQueryWrapper = new QueryWrapper<>();
        lyRyQueryWrapper.eq("is_delete",false);
        List<LyRy> lyRIES = lyRyMapper.selectList(lyRyQueryWrapper);
        lyRIES.stream().forEach(lyRy1 -> {
            if (lyRy1.getZjId().equals(lyRy.getZjId())&&lyRy1.getHdId().equals(lyRy.getHdId())) {
                throw new WebException(ZJ_HD_EXISTS_ERROR);
            }
        });
        if (!lyRyAdminService.saveRy(lyRy)) {
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
    @ApiOperation(value = "更新人员")
    public ResponseUtil update(@RequestBody LyRy lyRy) throws WebException {
        Long id = lyRy.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (lyRyAdminService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
        if (!lyRyAdminService.updateById(lyRy)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新人员信息成功!");

    }
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) {

        LoginUser loginUser = SystemConstant.threadLocal.get();

        if (loginUser == null) {
            throw new WebException(ErrorCodeEnum.NOT_LOGIN_ERROR);
        }

        LyRy old = lyRyAdminService.getById(id);
        if (old == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }

        old.setIsDelete(true);
        old.setGxsj(new Date());

        lyRyAdminService.updateById(old);

        return ResponseUtil.success(old);
    }
}

