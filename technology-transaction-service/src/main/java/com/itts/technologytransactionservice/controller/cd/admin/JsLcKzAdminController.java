package com.itts.technologytransactionservice.controller.cd.admin;


import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.controller.test.bid.BidController;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsLcKz;
import com.itts.technologytransactionservice.model.TJsLcKzDto;
import com.itts.technologytransactionservice.service.cd.JsCjRcAdminService;
import com.itts.technologytransactionservice.service.cd.JsLcKzAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/JsLcKz")
@Api(value = "JsLcKzAdminController", tags = "活动流程控制后台")
@RestController
public class JsLcKzAdminController extends BaseController {
    @Autowired
    private BidController bidController;

    @Autowired
    private JsLcKzAdminService jsLcKzAdminService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsLcKzAdminService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsLcKzAdminService.getById(new BigInteger(id)));
    }

    /**
     * 开始竞拍逻辑
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLcKzDto tJsLcKzDto) throws IOException {
        if(jsLcKzAdminService.saveLcKz(tJsLcKzDto)){
            bidController.onMessage("开始成功，调用刷新活动流程控制状态方法");
            return save(true);
        }else{
            return save(false);
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsLcKz tJsLcKz) throws IOException {
        if(jsLcKzAdminService.updateById(tJsLcKz)){
            bidController.onMessage("保存成功，调用刷新活动流程控制状态方法");
            return update(true);
        }
        return update(true);
    }
    /**
     * 落锤逻辑
     */
    @RequestMapping("/lcUpdate")
    public R lcUpdate(@RequestBody TJsLcKzDto tJsLcKzDto) throws IOException {
        if(jsLcKzAdminService.updateLc(tJsLcKzDto)){
            bidController.onMessage("落锤成功，调用刷新活动流程控制状态方法");
            return update(true);
        }
        return update(true);
    }
    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsLcKzAdminService.removeByIdLcKz(id));
    }



}
