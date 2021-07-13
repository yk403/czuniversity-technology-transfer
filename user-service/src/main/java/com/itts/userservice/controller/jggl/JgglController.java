package com.itts.userservice.controller.jggl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.itts.common.constant.SystemConstant.threadLocal;

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
     * 获取机构列表
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取机构列表")
    public ResponseUtil getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "string", required = false) String string,
                                @RequestParam(value = "jgbm", required = false) String jgbm,
                                @RequestParam(value = "jglb",required = false)String jglb,
                                @RequestParam(value = "lx",required = false)String lx) {

        PageInfo<Jggl> byPage = jgglService.findPage(pageNum, pageSize, jgbm,string,jglb,lx);

        return ResponseUtil.success(byPage);
    }

    /**
     * 查询机构，通过名称和编码
     */
    @GetMapping("/query/mechanism/")
    @ApiOperation(value = "通过名称和编码查询机构")
    public ResponseUtil getBynameandcode(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                         @RequestParam("string") String string) throws WebException {
        if (string == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        PageInfo<Jggl> jggls = jgglService.selectByString(pageNum, pageSize, string);
        return ResponseUtil.success(jggls);
    }

    /**
     * 获取机构树
     */
    @GetMapping("/tree/")
    @ApiOperation(value = "获取机构树")
    public ResponseUtil findJgglVO(@RequestParam(required = false) String jgbm) {
        List<JgglVO> jgglVO = jgglService.findJgglVO(jgbm);
        return ResponseUtil.success(jgglVO);
    }

    /**
     * 获取机构列表
     */
    @GetMapping("/list/")
    @ApiOperation(value = "获取机构列表")
    public ResponseUtil getlist(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "jgbm", required = false) String jgbm) {

        PageInfo<Jggl> byPage = jgglService.findByPage(pageNum, pageSize, jgbm);

        return ResponseUtil.success(byPage);
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


        /*Jggl checkJgbm = jgglService.selectByJgbm(jggl.getJgbm());
        if (checkJgbm != null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_FIND_ERROR);
        }*/

        if (Objects.equals(jggl.getFjbm(), UserServiceCommon.GROUP_SUPER_PARENT_CODE)) {
            String str= getByJggl(jggl);
            jggl.setJgbm(str);
            jggl.setCj(jggl.getJgbm());


        } else {

            //获取父级机构的层级和编码
            Jggl jggl1 = jgglService.selectByJgbm(jggl.getFjbm());

            String cj = jggl1.getCj();
            String jgbm1 = jggl1.getJgbm();
            //生成机构编码
            String str= getByJggl(jggl);
            jggl.setJgbm(jgbm1 + str);
            //生成层级
            String jgbm = jggl.getJgbm();
            cj = cj + "-" + jgbm;
            jggl.setCj(cj);
            jggl.setFjmc(jggl1.getJgmc());

        }

        jggl.setGxsj(new Date());
        jggl.setCjsj(new Date());

        LoginUser loginUser = threadLocal.get();
        if (loginUser.getUserId() != null) {
            jggl.setCjr(loginUser.getUserId());
            jggl.setGxr(loginUser.getUserId());
        }

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
        //父级编码修改情况区分
        if (jggl.getFjbm().equals(jggl.getJgbm())) {
            BeanUtils.copyProperties(jggl, group, "id", "cjsj", "cjr", "fjbm");
            group.setGxsj(new Date());
            jgglService.update(group);
            return ResponseUtil.success(group);
        } else if(jggl.getFjbm().equals(group.getFjbm())){
            BeanUtils.copyProperties(jggl, group, "id", "cjsj", "cjr");
            group.setGxsj(new Date());
            jgglService.update(group);
            return ResponseUtil.success(group);
        } else {
            //获取当前机构的所有子机构
            List<Jggl> list = jgglService.getList(cjold);
            //获取父级机构的层级
            String fjbm = jggl.getFjbm();
            String cj;
            Jggl fatherGroup;
            //判断机构是否移入自身的子机构
            Boolean flag =false;
            for (Jggl jggl1 : list) {
                if (jggl.getFjbm().equals(jggl1.getJgbm())) {
                    flag = true;
                }
            }
            if(flag){
                Long id1 = jgglService.selectByJgbm(jggl.getFjbm()).getId();
                List<Jggl> jgglList = new ArrayList<Jggl>();
                for (Jggl jggl1 : list) {
                    if (jggl.getFjbm().equals(jggl1.getJgbm())) {
                        if (jggl1.getFjbm().equals(group.getJgbm())) {
                            jgglList.add(jggl1);
                        }
                    }
                }
                if (jgglList!=null) {
                    //修改机构自身下一级的所有子机构的fjbm和fjmc
                    for (int i = 0; i < jgglList.size(); i++) {
                        Jggl jggl1 = jgglList.get(i);
                        jggl1.setFjbm(group.getFjbm());
                        if(Objects.equals(group.getFjbm(), UserServiceCommon.GROUP_SUPER_PARENT_CODE)){
                            jggl1.setFjmc(UserServiceCommon.GROUP_SUPER_PARENT_CODE);
                        }else {
                            jggl1.setFjmc(jgglService.selectByJgbm(group.getFjbm()).getJgmc());
                        }
                        jgglService.update(jggl1);
                    }
                    //修改所有子机构的机构编码和层级
                    for (int i = 0; i < list.size(); i++) {
                        Jggl old = list.get(i);
                        if (old.getJgbm() == group.getJgbm()) {
                            list.remove(i);
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Jggl old = list.get(i);
                        if(Objects.equals(group.getFjbm(), UserServiceCommon.GROUP_SUPER_PARENT_CODE)){
                            old.setJgbm(getByJggl(UserServiceCommon.GROUP_SUPER_PARENT_CODE,i+1));
                        }else {
                            old.setJgbm(group.getFjbm()+getByJggl(group.getFjbm(),i+1));
                        }
                        if(Objects.equals(group.getFjbm(), UserServiceCommon.GROUP_SUPER_PARENT_CODE)){
                            old.setCj(old.getJgbm());
                        }else {
                            old.setCj(StringUtils.strip(old.getCj(), group.getJgbm() + "-"));
                        }
                        jgglService.update(old);
                    }
                    //修改自身机构编码,父机构名称和层级
                    Jggl jggl1 = jgglService.get(id1);
                    jggl.setJgbm(jggl1.getJgbm() + getByJggl(jggl));
                    jggl.setFjbm(jggl1.getJgbm());
                    jggl.setCj(jggl1.getCj() + "-" + jggl.getJgbm());
                    jggl.setFjmc(jggl1.getJgmc());
                    jgglService.update(jggl);
                    return ResponseUtil.success(jggl);
                }
            }else {
                if(Objects.equals(fjbm, UserServiceCommon.GROUP_SUPER_PARENT_CODE)){

                    jggl.setJgbm(getByJggl(UserServiceCommon.GROUP_SUPER_PARENT_CODE));
                    jggl.setFjmc(UserServiceCommon.GROUP_SUPER_PARENT_CODE);
                    jggl.setCj(jggl.getJgbm());
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Jggl jggl1 = list.get(i);
                            if (cjold == jggl1.getCj()) {
                                list.remove(i);
                            }
                        }
                        if(list!=null){
                            for (int i = 0; i < list.size(); i++) {
                                Jggl jggl1 = list.get(i);
                                jggl1.setJgbm(getByJggl(jggl.getJgbm(),i+1));
                                jggl1.setCj(jggl1.getCj().replace(cjold, jggl.getCj()));

                                jgglService.update(jggl1);
                            }
                        }
                    }

                } else {
                    fatherGroup = jgglService.selectByJgbm(fjbm);
                    cj = fatherGroup.getCj();
                    jggl.setFjmc(fatherGroup.getJgmc());
                    jggl.setJgbm(fatherGroup.getJgbm()+getByJggl(fjbm));
                    cj = cj + "-" + jggl.getJgbm();
                    jggl.setCj(cj);
                    //生成层级
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Jggl jggl1 = list.get(i);
                            if (cjold == jggl1.getCj()){
                                list.remove(i);
                            }
                        }
                        if(list!=null){
                            for (int i = 0; i < list.size(); i++) {
                                Jggl jggl1 = list.get(i);
                                jggl1.setCj(jggl1.getCj().replace(cjold, cj));
                                jggl1.setJgbm(jggl.getJgbm()+getByJggl(jggl.getJgbm(),i+1));
                                jgglService.update(jggl1);
                            }
                        }
                    }
                }
            }
            jggl.setGxsj(new Date());
            BeanUtils.copyProperties(jggl, group, "id", "cjsj", "cjr");
            jgglService.update(group);

            return ResponseUtil.success(group);
        }
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
        objectQueryWrapper.eq("fjbm", jgbm)
                .eq("sfsc", false);
        Jggl jggl1 = jgglMapper.selectOne(objectQueryWrapper);
        if (jggl1 != null) {
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

        if (Objects.equals(jggl.getJgbm(), UserServiceCommon.GROUP_SUPER_PARENT_CODE)) {
            throw new WebException(ErrorCodeEnum.SYSTEM_FIND_ERROR);
        }

        if (jggl.getFjbm() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }

        if (jggl.getJgmc() == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }

    /**
     * 生成机构编码后三位
     * @param jggl
     * @return
     */
    private String getByJggl(Jggl jggl){
        QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
        jgglQueryWrapper.eq("fjbm",jggl.getFjbm());
        List<Jggl> jgglList = jgglMapper.selectList(jgglQueryWrapper);
        String str = String.format("%03d", jgglList.size() + 1);
        return str;
    }
    /**
     * 生成机构编码后三位
     * @param fjbm
     * @return
     */
    private String getByJggl(String fjbm){
        QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
        jgglQueryWrapper.eq("fjbm",fjbm);
        List<Jggl> jgglList = jgglMapper.selectList(jgglQueryWrapper);
        String str = String.format("%03d", jgglList.size() + 1);
        return str;
    }
    /**
     * 生成机构编码后三位
     * @param jggl
     * @return
     */
    private String getByJggl(Jggl jggl,int i){
        QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
        jgglQueryWrapper.eq("fjbm",jggl.getFjbm());
        List<Jggl> jgglList = jgglMapper.selectList(jgglQueryWrapper);
        String str = String.format("%03d", jgglList.size() + i);
        return str;
    }
    /**
     * 生成机构编码后三位
     * @param fjbm
     * @return
     */
    private String getByJggl(String fjbm,int i){
        QueryWrapper<Jggl> jgglQueryWrapper = new QueryWrapper<>();
        jgglQueryWrapper.eq("fjbm",fjbm);
        List<Jggl> jgglList = jgglMapper.selectList(jgglQueryWrapper);
        String str = String.format("%03d", jgglList.size() + i);
        return str;
    }
}

