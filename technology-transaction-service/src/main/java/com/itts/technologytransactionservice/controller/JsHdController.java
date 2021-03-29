package com.itts.technologytransactionservice.controller;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.JsHdService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术活动管理
 */

@RequestMapping(BASE_URL+"/v1/JsHd")
@Api(value = "JsHdController", tags = "技术活动管理")
@RestController
public class JsHdController extends BaseController {
    @Autowired
    private JsHdService jsHdService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<TJsHd> page = jsHdService.page(query);
        return ResponseUtil.success(page);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TJsHd tJsHd = jsHdService.getById(id);
        return success(tJsHd);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsHd tJsHd) {
        boolean result = jsHdService.save(tJsHd);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsHd tJsHd) {
        boolean result = jsHdService.updateById(tJsHd);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = jsHdService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = jsHdService.removeByIds(ids);
        return  remove(result);
    }

}
