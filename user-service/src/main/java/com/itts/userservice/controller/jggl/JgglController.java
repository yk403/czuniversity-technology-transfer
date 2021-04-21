package com.itts.userservice.controller.jggl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.common.UserServiceCommon;
import com.itts.userservice.mapper.jggl.JgglMapper;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.service.jggl.JgglService;
import com.itts.userservice.vo.JgglVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
@Api(tags = "机构管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/jggl")
public class JgglController {

    @Resource
    private JgglService jgglService;

    @Resource
    private JgglMapper jgglMapper;
    /**
     * 查询机构，通过名称和编码
     */
    @GetMapping("/queryMechanism/")
    @ApiOperation(value = "通过名称和编码查询机构")
    public ResponseUtil getBynameandcode(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam("string") String string) throws WebException {
        if(string==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PageInfo<Jggl> jggls = jgglService.selectByString(pageNum, pageSize, string);
        return ResponseUtil.success(jggls);
    }

    /**
     * 获取机构树
     */
    @GetMapping("/treeList/")
    @ApiOperation(value = "获取机构树")
    public ResponseUtil findJgglVO(@RequestParam(required = false) String jgbm) {
        List<JgglVO> jgglVO = jgglService.findJgglVO(jgbm);
        return ResponseUtil.success(jgglVO);
    }

    /**
     * 获取机构列表
     */
    @GetMapping("/getlist/")
    @ApiOperation(value = "获取机构列表")
    public ResponseUtil getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(required = false) String jgbm) {

        PageInfo<Jggl> byPage = jgglService.findByPage(pageNum, pageSize, jgbm);
        return ResponseUtil.success(byPage);
    }

    /**
     * 查询机构编码是否重复
     */
    @GetMapping("/checkjgbm/")
    @ApiOperation(value = "查询机构编码是否重复")
    public ResponseUtil checkJgglbm(@RequestParam String jgbm) {
        if (jgbm == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //机构不重复，返回否
        if (jgglService.selectByJgbm(jgbm) == null) {
            return ResponseUtil.success(false);
        }
        return ResponseUtil.success(true);
    }

    /**
     * 获取详情
     *
     * @param id
     * @author fl
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "获取详情")
    public ResponseUtil get(@PathVariable("id") Long id) {
        Jggl jggl = jgglService.get(id);
        return ResponseUtil.success(jggl);
    }

    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Jggl jggl) throws WebException {

        checkPequest(jggl);
        if(jgglService.selectByJgmc(jggl.getJgbm())!=null){
            throw new WebException(ErrorCodeEnum.SYSTEM_FIND_ERROR);
        }

        if (Objects.equals(jggl.getFjbm(), UserServiceCommon.GROUP_SUPER_PARENT_CODE)) {

            jggl.setCj(jggl.getJgbm());
        } else {

            //获取父级机构的层级
            Jggl jggl1 = jgglService.selectByJgbm(jggl.getFjbm());

            String cj = jggl1.getCj();

            //生成层级
            String jgbm = jggl.getJgbm();
            cj = cj + "-"+jgbm;
            jggl.setCj(cj);
        }
        jggl.setGxsj(new Date());
        jggl.setCjsj(new Date());
        Jggl add = jgglService.add(jggl);
        return ResponseUtil.success(add);
    }

    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Jggl jggl) throws WebException {

        checkPequest(jggl);

        Long id = jggl.getId();

        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        //检查数据库中是否存在要更新的数据
        Jggl group = jgglService.get(id);
        if (group == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        String cjold = group.getCj();
        //获取当前机构的所有子机构
        List<Jggl> list = jgglService.getList(cjold);
        //获取父级机构的层级
        String fjbm = jggl.getFjbm();
        Jggl fatherGroup = jgglService.selectByJgbm(fjbm);
        String cj = fatherGroup.getCj();

        BeanUtils.copyProperties(jggl, group, "id", "cjsj", "cjr");

        if (!Objects.equals(fjbm, UserServiceCommon.GROUP_SUPER_PARENT_CODE)) {


            //生成层级

            if(list!=null){
                list.forEach(Jggl ->{
                    if(cjold==Jggl.getCj()){
                        return;
                    }
                    Jggl.setCj(Jggl.getCj().replace(cjold, fatherGroup.getCj()));
                    jgglService.update(Jggl);
                });
            }


            cj = cj +"-"+ jggl.getJgbm();
            jggl.setCj(cj);


        } else {
            if(list!=null){
                list.forEach(Jggl ->{
                    if(cjold==Jggl.getCj()){
                        return;
                    }
                    Jggl.setCj(Jggl.getCj().replace(cjold, fatherGroup.getCj()));
                    jgglService.update(Jggl);
                });
            }

            jggl.setCj(jggl.getJgbm());
        }
        group.setGxsj(new Date());
        jgglService.update(group);

        return ResponseUtil.success(group);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        //检查参数是否为空
        if (id == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Jggl jggl = jgglService.get(id);
        if (jggl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        //查询机构是否有子机构
        String jgbm = jggl.getJgbm();
        QueryWrapper<Jggl> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("fjbm",jgbm)
        .eq("sfsc",false);
        Jggl jggl1 = jgglMapper.selectOne(objectQueryWrapper);
        if(jggl1!=null){
            return ResponseUtil.error(ErrorCodeEnum.USER_DELETE_GROUP_HAVE_CHILD_ERROR);
        }
            jggl.setSfsc(true);
            jggl.setGxsj(new Date());
            jgglService.update(jggl);
        return ResponseUtil.success();
    }

    /**
     * 批量删除
     *//*
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/deletemore/")
    public ResponseUtil deletemore(@RequestBody List<Long> ids) throws WebException {
        ids.forEach(id -> {
            Jggl jggl = jgglService.get(id);
            jggl.setSfsc(true);
            jggl.setGxsj(new Date());
            jgglService.update(jggl);
        });

        return ResponseUtil.success();
    }*/

    /**
     * 校验参数是否合法
     */
    private void checkPequest(Jggl jggl) throws WebException {
        //如果参数为空，抛出异常
        if (jggl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (jggl.getFjbm() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (jggl.getJgmc() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

