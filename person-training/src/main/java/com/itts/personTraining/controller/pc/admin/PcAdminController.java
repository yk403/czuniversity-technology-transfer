package com.itts.personTraining.controller.pc.admin;

import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.personTraining.model.kc.Kc;
import com.itts.personTraining.model.pc.Pc;
import com.itts.personTraining.service.pc.PcService;
import com.itts.personTraining.service.sjzd.SjzdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.itts.common.enums.ErrorCodeEnum.*;

/**
 * <p>
 * 批次表 前端控制器
 * </p>
 *
 * @author FL
 * @since 2021-04-20
 */
@RestController
@RequestMapping(SystemConstant.ADMIN_BASE_URL+"/v1/pc")
@Api(value = "PcAdminController", tags = "批次后台管理")
public class PcAdminController {

    @Resource
    private PcService pcService;
    @Autowired
    private SjzdService sjzdService;

    @GetMapping("/list/")
    @ApiModelProperty(value = "查询批次列表")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "name", required = false) String name){
        return ResponseUtil.success(pcService.findByPage(pageNum, pageSize, name));
    }

    /**
     * 查询未录入批次
     */
    @GetMapping("/findPcs")
    @ApiModelProperty(value = "查询未录入批次")
    public ResponseUtil findPcs(){
        return ResponseUtil.success(pcService.findPcs());
    }

    /**
     * 根据id获取批次详情
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "根据id获取批次详情")
    public ResponseUtil getByOne(@PathVariable("id")Long id){
        Pc pc = pcService.get(id);
        return ResponseUtil.success(pc);
    }

    /**
     * 根据id查询课程信息列表
     * @param id
     * @return
     */
    @GetMapping("/getKcListById/{id}")
    @ApiOperation(value = "根据id查询课程信息列表")
    public ResponseUtil getKcListById(@PathVariable("id")Long id){
        List<Kc> kcList = pcService.getKcListById(id);
        return ResponseUtil.success(kcList);
    }

    /**
     * 获取所有批次详情
     * @return
     */
    @GetMapping("/getAll")
    @ApiOperation(value = "获取所有批次详情")
    public ResponseUtil getAll(){
        List<Pc> pcs = pcService.getAll();
        return ResponseUtil.success(pcs);
    }

    /**
     * 根据教育类型查询批次信息
     * @param jylx
     * @return
     */
    @GetMapping("/getByJylx/{jylx}")
    @ApiOperation(value = "根据教育类型查询批次信息")
    public ResponseUtil getByJylx(@PathVariable("jylx")String jylx){
        if (jylx == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(pcService.getByJylx(jylx));
    }

    /**
     * 根据字典项类型查询类型
     * @param pageNum
     * @param pageSize
     * @param model
     * @param systemType
     * @param dictionary
     * @param zdbm
     * @param parentId
     * @param request
     * @return
     */
    @GetMapping("/getByDictionary")
    @ApiOperation(value = "根据字典项类型查询类型")
    public ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @ApiParam(value = "模块类型") @RequestParam(value = "model", required = false) String model,
                         @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                         @ApiParam(value = "所属模块编码") @RequestParam(value = "dictionary",required = false) String dictionary,
                         @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                         @ApiParam(value = "父级字典ID") @RequestParam(value = "parentId", required = false) Long parentId,
                         HttpServletRequest request){

        return sjzdService.getList(pageNum, pageSize, model, systemType, dictionary, zdbm, parentId, request.getHeader("token"));
    }
    /**
     * 新增批次
     * @param pc
     * @return
     * @throws WebException
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增批次")
    public ResponseUtil add(@RequestBody Pc pc)throws WebException{
        checkRequset(pc);
        if (!pcService.add(pc)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增批次成功!");
    }

    /**
     * 更新批次
     * @param pc
     * @return
     * @throws WebException
     */
    @ApiOperation(value = "更新批次")
    @PutMapping("/update")
    public ResponseUtil update(@RequestBody Pc pc)throws WebException{
        Pc old = pcService.get(pc.getId());
        if(old==null){
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!pcService.update(pc)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("更新批次成功!");
    }
    /**
     * 删除
     */
    @ApiOperation(value ="删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException {
        if(id==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        Pc pc = pcService.get(id);
        if(pc==null){
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        if (!pcService.delete(pc)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("删除批次成功!");
    }
    /**
     * 批量删除
     */
    @ApiOperation(value = "批量删除")
    @DeleteMapping("/deleteBatch/")
    public ResponseUtil deleteBatch(@RequestBody List ids)throws WebException{
        if(ids==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!pcService.updateBatch(ids)) {
            throw new WebException(DELETE_FAIL);
        }
        return ResponseUtil.success("批量删除成功!");
    }
    /**
     * 校验参数是否合法
     */
    private void checkRequset(Pc pc)throws WebException{
        if(pc==null){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        List<Pc> pcList = pcService.getAll();
        for (Pc pc1 : pcList) {
            if (pc1.getPch().equals(pc.getPch())) {
                throw new WebException(BATCH_NUMBER_EXISTS_ERROR);
            }
            if (pc1.getPcmc().equals(pc.getPcmc())) {
                throw new WebException(BATCH_NAME_EXISTS_ERROR);
            }
        }
    }
}

