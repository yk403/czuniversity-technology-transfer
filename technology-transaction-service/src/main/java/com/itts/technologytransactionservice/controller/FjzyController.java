package com.itts.technologytransactionservice.controller;


import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.service.FjzyService;
import com.itts.technologytransactionservice.service.JsBmService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: yukai
 * @Data: 2021/3/26
 * @Description: 附件资源管理
 */

@RequestMapping(BASE_URL+"/v1/Fjzy")
@Api(value = "FjzyController", tags = "附件资源管理")
@RestController
public class FjzyController extends BaseController {

    @Autowired
    private FjzyService fjzyService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //params.put("userId",2);
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(fjzyService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(fjzyService.getById(Long.valueOf(id)));
    }


}
