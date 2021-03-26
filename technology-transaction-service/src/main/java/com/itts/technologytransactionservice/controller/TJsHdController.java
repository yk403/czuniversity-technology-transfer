package com.itts.technologytransactionservice.controller;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.ITJsHdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:17:27
 */

@RestController
@RequestMapping("/back/tJsHd")
public class TJsHdController extends BaseController {
    @Autowired
    private ITJsHdService tJsHdService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<TJsHd> page = tJsHdService.page(query);
        return ResponseUtil.success(page);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TJsHd tJsHd = tJsHdService.getById(id);
        return success(tJsHd);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsHd tJsHd) {
        boolean result = tJsHdService.save(tJsHd);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsHd tJsHd) {
        boolean result = tJsHdService.updateById(tJsHd);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = tJsHdService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = tJsHdService.removeByIds(ids);
        return  remove(result);
    }



}
