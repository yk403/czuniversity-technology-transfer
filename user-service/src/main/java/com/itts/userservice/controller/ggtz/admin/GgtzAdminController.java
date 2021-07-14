package com.itts.userservice.controller.ggtz.admin;



import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.feign.persontraining.ggtz.GgtzRpcService;
import com.itts.userservice.model.ggtz.Ggtz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fuli
 * @since 2021-07-12
 */
@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/ggtz")
@Api(value = "GgtzAdminController", tags = "公告通知后台管理")
public class GgtzAdminController {

    @Resource
    private GgtzRpcService ggtzRpcService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "jgId",required = false) Long jgId,
                                @RequestParam(value = "zt",required = false) String zt,
                                @RequestParam(value = "lx",required = false) String lx) throws WebException {
        return ggtzRpcService.getList(pageNum, pageSize, jgId, zt, lx);
    }
    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Ggtz ggtz) throws WebException{
        return ggtzRpcService.add(ggtz);
    }
    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Ggtz ggtz) throws WebException{
        return ggtzRpcService.update(ggtz);
    }
    /**
     * 发布
     */
    @ApiOperation(value = "发布")
    @PutMapping("/release/{id}")
    public ResponseUtil release(@PathVariable("id") Long id) throws WebException{
        return ggtzRpcService.release(id);
    }
    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/stop/{id}")
    public ResponseUtil stop(@PathVariable("id") Long id) throws WebException{
        return ggtzRpcService.stop(id);
    }
    /**
     * 查询
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询")
    public ResponseUtil get(@PathVariable("id") Long id) throws WebException{
        return ggtzRpcService.get(id);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException{
        return ggtzRpcService.delete(id);
    }

}

