package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.JsLbService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/JsLb")
@Api(value = "JsLbController", tags = "技术类别管理")
@RestController
public class JsLbController extends BaseController {
    @Autowired
    private JsLbService jsLbService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<TJsLb> page = jsLbService.page(query);
        return ResponseUtil.success(page);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        TJsLb tJsLb = jsLbService.getById(l);
        return success(tJsLb);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLb tJsLb) {
        boolean result = jsLbService.save(tJsLb);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsLb tJsLb) {
        boolean result = jsLbService.updateById(tJsLb);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = jsLbService.removeById(l);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){
        ArrayList<Long> longs = new ArrayList<>();
        for (String id:
                ids) {
            long l = Long.parseLong(id);
            longs.add(l);
        }
        boolean result = jsLbService.removeByIds(longs);
        return  remove(result);
    }



}
