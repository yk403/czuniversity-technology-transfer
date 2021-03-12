package com.itts.technologytransactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TQy;
import com.itts.technologytransactionservice.service.ITQyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:59:20
 */

@RestController
@RequestMapping("/back/tQy")
public class TQyController extends BaseController {
    @Autowired
    private ITQyService tQyService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TQy> tQyIPage = tQyService.page(query);
        return success(tQyIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TQy tQy = tQyService.getById(id);
        return success(tQy);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TQy tQy) {
        boolean result = tQyService.save(tQy);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TQy tQy) {
        boolean result = tQyService.updateById(tQy);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = tQyService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = tQyService.removeByIds(ids);
        return  remove(result);
    }



}
