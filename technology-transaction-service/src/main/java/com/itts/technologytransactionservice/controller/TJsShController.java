package com.itts.technologytransactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.ITJsShService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:13:04
 */

@RestController
@RequestMapping("/back/tJsSh")
public class TJsShController extends BaseController {
    @Autowired
    private ITJsShService tJsShService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsSh> tJsShIPage = tJsShService.page(query);
        return success(tJsShIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        TJsSh tJsSh = tJsShService.getById(id);
        return success(tJsSh);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsSh tJsSh) {
        boolean result = tJsShService.save(tJsSh);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsSh tJsSh) {
        boolean result = tJsShService.updateById(tJsSh);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = tJsShService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = tJsShService.removeByIds(ids);
        return  remove(result);
    }

}
