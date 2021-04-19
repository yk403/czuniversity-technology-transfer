package com.itts.technologytransactionservice.controller.cd.admin;


import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.service.JsBmService;
import com.itts.technologytransactionservice.service.cd.JsBmAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/JsBm")
@Api(value = "JsBmAdminController", tags = "活动报名后台管理")
@RestController
public class JsBmAdminController extends BaseController {

    @Autowired
    private JsBmAdminService jsBmAdminService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsBmAdminService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsBmAdminService.getById(Integer.parseInt(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsBm tJsBm) {
        return save(jsBmAdminService.save(tJsBm));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsBm tJsBm) {
        return update(jsBmAdminService.updateById(tJsBm));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsBmAdminService.removeByIdBm(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){
        return  remove(jsBmAdminService.removeByIdsBm(ids));
    }

}
