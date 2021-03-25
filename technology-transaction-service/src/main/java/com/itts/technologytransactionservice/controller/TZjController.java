package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TZj;
import com.itts.technologytransactionservice.service.ITZjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-20 17:05:41
 */

@RestController
@RequestMapping("/back/tZj")
public class TZjController extends BaseController {
    @Autowired
    private ITZjService tZjService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TZj> tZjIPage = tZjService.page(query);
        return success(tZjIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TZj tZj = tZjService.getById(id);
        return success(tZj);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TZj tZj) {
        boolean result = tZjService.save(tZj);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TZj tZj) {
        boolean result = tZjService.updateById(tZj);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = tZjService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = tZjService.removeByIds(ids);
        return  remove(result);
    }

}
