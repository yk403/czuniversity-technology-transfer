package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TJsJj;
import com.itts.technologytransactionservice.service.ITJsJjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:15:49
 */

@RestController
@RequestMapping("/back/tJsJj")
public class TJsJjController extends BaseController {
    @Autowired
    private ITJsJjService tJsJjService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsJj> tJsJjIPage = tJsJjService.page(query);
        return success(tJsJjIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TJsJj tJsJj = tJsJjService.getById(id);
        return success(tJsJj);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsJj tJsJj) {
        boolean result = tJsJjService.save(tJsJj);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsJj tJsJj) {
        boolean result = tJsJjService.updateById(tJsJj);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = tJsJjService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = tJsJjService.removeByIds(ids);
        return  remove(result);
    }



}
