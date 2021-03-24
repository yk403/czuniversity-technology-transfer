package com.itts.technologytransactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsLy;
import com.itts.technologytransactionservice.service.ITJsLyService;
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
 * @date 2021-02-22 11:11:25
 */

@RestController
@RequestMapping("/back/tJsLy")
public class TJsLyController extends BaseController {
    @Autowired
    private ITJsLyService tJsLyService;

    /**
    * (前台)分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<TJsLy> page = tJsLyService.page(query);
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
        TJsLy tJsLy = tJsLyService.getById(l);
        return success(tJsLy);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLy tJsLy) {
        boolean result = tJsLyService.save(tJsLy);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsLy tJsLy) {
        boolean result = tJsLyService.updateById(tJsLy);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsLyService.removeById(l);
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
        boolean result = tJsLyService.removeByIds(longs);
        return  remove(result);
    }



}
