package com.itts.userservice.controller.xwgl.admin;

import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.feign.persontraining.xwgl.XwglRpcService;
import com.itts.userservice.model.xwgl.Xwgl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

@RestController
@RequestMapping(ADMIN_BASE_URL +"/v1/xwgl")
@Api(value = "XwglAdminController", tags = "新闻管理后台")
public class XwglAdminController {

    @Resource
    private XwglRpcService xwglRpcService;

    /**
     * 查询
     */
    @GetMapping("/list/")
    @ApiOperation(value = "查询新闻")
    public ResponseUtil getList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                @RequestParam(value = "jgId",required = false) Long jgId,
                                @RequestParam(value = "zt",required = false) String zt,
                                @RequestParam(value = "lx",required = false) String lx) throws WebException {
        return xwglRpcService.getList(pageNum, pageSize, jgId, zt, lx);
    }
    /**
     * 新增
     */
    @PostMapping("/add/")
    @ApiOperation(value = "新增")
    public ResponseUtil add(@RequestBody Xwgl xwgl) throws WebException{
        return xwglRpcService.add(xwgl);
    }
    /**
     * 更新
     */
    @ApiOperation(value = "更新")
    @PutMapping("/update/")
    public ResponseUtil update(@RequestBody Xwgl xwgl) throws WebException{
        return xwglRpcService.update(xwgl);
    }
    /**
     * 发布
     */
    @ApiOperation(value = "发布")
    @PutMapping("/release/{id}")
    public ResponseUtil release(@PathVariable("id") Long id) throws WebException{
        return xwglRpcService.release(id);
    }
    /**
     * 停用
     */
    @ApiOperation(value = "停用")
    @PutMapping("/stop/{id}")
    public ResponseUtil stop(@PathVariable("id") Long id) throws WebException{
        return xwglRpcService.stop(id);
    }
    /**
     * 查询
     */
    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询")
    public ResponseUtil get(@PathVariable("id") Long id) throws WebException{
        return xwglRpcService.get(id);
    }
    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil delete(@PathVariable("id") Long id) throws WebException{
        return xwglRpcService.delete(id);
    }

}
