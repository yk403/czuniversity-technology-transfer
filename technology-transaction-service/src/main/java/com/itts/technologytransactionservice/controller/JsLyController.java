package com.itts.technologytransactionservice.controller;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsLy;
import com.itts.technologytransactionservice.service.JsLyService;
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
 * @Description: 技术领域管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/JsLy")
@Api(value = "JsLyController", tags = "技术领域管理")
@RestController
public class JsLyController extends BaseController {
    @Autowired
    private JsLyService jsLyService;

    /**
    * (前台)分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        PageInfo<TJsLy> page = jsLyService.page(query);
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
        TJsLy tJsLy = jsLyService.getById(l);
        return success(tJsLy);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLy tJsLy) {
        boolean result = jsLyService.save(tJsLy);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsLy tJsLy) {
        boolean result = jsLyService.updateById(tJsLy);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = jsLyService.removeById(l);
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
        boolean result = jsLyService.removeByIds(longs);
        return  remove(result);
    }



}
