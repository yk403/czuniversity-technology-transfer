package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.ITJsLbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:14:18
 */

@RestController
@RequestMapping("/back/tJsLb")
public class TJsLbController extends BaseController {
    @Autowired
    private ITJsLbService tJsLbService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsLb> tJsLbIPage = tJsLbService.page(query);
        return success(tJsLbIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        TJsLb tJsLb = tJsLbService.getById(l);
        return success(tJsLb);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLb tJsLb) {
        boolean result = tJsLbService.save(tJsLb);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsLb tJsLb) {
        boolean result = tJsLbService.updateById(tJsLb);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsLbService.removeById(l);
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
        boolean result = tJsLbService.removeByIds(longs);
        return  remove(result);
    }



}
