package com.itts.userservice.controller.jggl;


import com.itts.common.constant.SystemConstant;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.cz.Cz;
import com.itts.userservice.model.jggl.Jggl;
import com.itts.userservice.model.yh.Yh;
import com.itts.userservice.service.jggl.JgglService;
import com.itts.userservice.vo.JgglVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fl
 * @since 2021-03-30
 */
@Api(tags = "机构管理")
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/v1/jggl")
public class JgglController {

    @Resource
    private JgglService jgglService;
    /**
     * 查询机构，通过名称和编码
     */
    @GetMapping("/queryMechanism/{string}")
    @ApiOperation(value="通过名称和编码查询机构")
    public ResponseUtil getBynameandcode(@PathVariable("string") String string) throws WebException{
        Jggl jggl = jgglService.selectByJgbm(string);
        if(jggl==null){
            Jggl jggl1 = jgglService.selectByJgmc(string);
            if(jggl1==null){
                throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
            }else{
                return ResponseUtil.success(jggl1);
            }
        }
        return ResponseUtil.success(jggl);
    }
    /**
     * 获取机构树
     *
     */
    @GetMapping("/treeList/")
    @ApiOperation(value = "获取机构树")
    public ResponseUtil findJgglVO(){
        List<JgglVO> jgglVO = jgglService.findJgglVO();
        return ResponseUtil.success(jgglVO);
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
    public ResponseUtil add(@RequestBody Jggl jggl)throws WebException{
        checkPequest(jggl);
        Jggl add = jgglService.add(jggl);
        return ResponseUtil.success(add);
    }
    /**
     * 更新
     */
     @ApiOperation(value="更新")
     @PutMapping("/update/")
     public ResponseUtil update(@RequestBody Jggl jggl)throws WebException{
         Long id = jggl.getId();
         if(id==null){
             throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
         }
         //检查数据库中是否存在要更新的数据
         Jggl jggl1 = jgglService.get(id);
         if (jggl1==null){
             throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
         }
         checkPequest(jggl);
         BeanUtils.copyProperties(jggl,jggl1,"id", "cjsj", "cjr");
         jgglService.update(jggl1);
         return ResponseUtil.success(jggl1);
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
        if(jggl==null){
            throw new WebException(ErrorCodeEnum.SYSTEM_NOT_FIND_ERROR);
        }
        jggl.setSfsc(false);
        jggl.setGxsj(new Date());
        jgglService.update(jggl);
        return ResponseUtil.success();
    }
    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/deletemore/")
    public ResponseUtil deletemore(@RequestBody List<Long> ids) throws WebException {
        ids.forEach(id->{
            Jggl jggl = jgglService.get(id);
            jggl.setSfsc(false);
            jggl.setGxsj(new Date());
            jgglService.update(jggl);
        });

        return ResponseUtil.success();
    }
    /**
     * 校验参数是否合法
     */
    private void checkPequest(Jggl jggl) throws WebException {
        //如果参数为空，抛出异常
        if (jggl == null) {
            throw new WebException(ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
    }
}

