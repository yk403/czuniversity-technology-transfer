package com.itts.technologytransactionservice.controller;


import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.test.bid.BidController;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsLcKz;
import com.itts.technologytransactionservice.service.JsCjRcService;
import com.itts.technologytransactionservice.service.JsLcKzService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.UNCHECK_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(BASE_URL+"/v1/JsLcKz")
@Api(value = "JsLcKzController", tags = "活动流程控制")
@RestController
public class JsLcKzController extends BaseController {
    @Autowired
    private BidController bidController;
    @Autowired
    private JsLcKzService jsLcKzService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsLcKzService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsLcKzService.getById(new BigInteger(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLcKz tJsLcKz) throws IOException {
        if(jsLcKzService.save(tJsLcKz)){
            bidController.onMessage("保存成功，调用刷新活动流程控制状态方法");
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
        if (jsLcKzService.updateById(tJsLcKz)){
            bidController.onMessage("保存成功，调用刷新活动流程控制状态方法");
            return update(true);
        }else{
            return update(false);
        }
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsLcKzService.removeByIdLcKz(id));
    }



}
