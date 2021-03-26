package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TJsJj;
import com.itts.technologytransactionservice.service.JsJjService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术经纪人管理
 */

@RequestMapping(BASE_URL+"/v1/JsJi")
@Api(value = "JsJjController", tags = "技术经纪人管理")
@RestController
public class JsJjController extends BaseController {
    @Autowired
    private JsJjService jsJjService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsJj> tJsJjIPage = jsJjService.page(query);
        return success(tJsJjIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TJsJj tJsJj = jsJjService.getById(id);
        return success(tJsJj);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsJj tJsJj) {
        boolean result = jsJjService.save(tJsJj);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsJj tJsJj) {
        boolean result = jsJjService.updateById(tJsJj);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = jsJjService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = jsJjService.removeByIds(ids);
        return  remove(result);
    }

}
