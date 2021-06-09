package com.itts.technologytransactionservice.controller.cd.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.Fjzy;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.service.cd.FjzyAdminService;
import com.itts.technologytransactionservice.service.cd.JsBmAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;


/**
 * @Author: yukai
 * @Data: 2021/3/26
 * @Description: 附件资源管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/Fjzy")
@Api(value = "FjzyAdminController", tags = "附件资源后台管理")
@RestController
public class FjzyAdminController extends BaseController {

    @Autowired
    private FjzyAdminService fjzyAdminService;

    /**
     * 获取列表
     */
    @ApiOperation(value = "获取列表")
    @PostMapping("/list")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(fjzyAdminService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") String id) {
        return ResponseUtil.success(fjzyAdminService.getById(Long.valueOf(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value ="新增")
    public ResponseUtil save(@RequestBody Fjzy fjzy) {
        return ResponseUtil.success(fjzyAdminService.save(fjzy));
    }

    /**
     * 更新附件
     *
     * @param lyBm
     * @return
     * @throws WebException
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新附件")
    public ResponseUtil update(@RequestBody Fjzy fjzy) {
        return ResponseUtil.success(fjzyAdminService.updateById(fjzy));
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @DeleteMapping("/delete/{id}")
    public ResponseUtil remove(@PathVariable("id") String id) {
        return ResponseUtil.success(fjzyAdminService.removeById(Long.valueOf(id)));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public ResponseUtil removeBatch(@RequestBody List<String> ids){
        return  ResponseUtil.success(fjzyAdminService.removeByIds(ids));
    }

}
