package com.itts.personTraining.controller.pc.admin;

import com.github.pagehelper.PageInfo;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
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
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        return ResponseUtil.success(pcService.findByPage(pageNum, pageSize));
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
     * 根据数据字典ID查询批次信息
     * @param sjzdId
     * @return
     */
    @GetMapping("/getBySjzdId/{sjzdId}")
    @ApiOperation(value = "根据批次类型查询批次信息")
    public ResponseUtil getBysjzdId(@PathVariable("sjzdId")Long sjzdId){
        if (sjzdId == null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(pcService.getBySjzdId(sjzdId));
    }

    /**
     * 根据字典项类型查询学生类型
     * @param pageNum
     * @param pageSize
     * @param model
     * @param systemType
     * @param dictionary
     * @param token
     * @return
     */
    @GetMapping("/getByDictionary")
    @ApiOperation(value = "根据字典项类型查询学生类型")
    public ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @ApiParam(value = "所属模块") @RequestParam(value = "model", required = false) String model,
                         @ApiParam(value = "所属系统") @RequestParam(value = "systemType", required = false) String systemType,
                         @ApiParam(value = "字典项类型") @RequestParam(value = "dictionary") String dictionary,
                         @ApiParam(value = "字典编码") @RequestParam(value = "zdbm", required = false) String zdbm,
                         @RequestHeader(name = "token") String token){
        return ResponseUtil.success(sjzdService.getList(pageNum, pageSize, model, systemType, dictionary, zdbm, token));
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
        checkRequset(pc);
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

