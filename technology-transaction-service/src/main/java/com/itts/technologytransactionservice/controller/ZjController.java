package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TZj;
import com.itts.technologytransactionservice.service.ZjService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 专家管理
 */

@RequestMapping(BASE_URL+"/v1/Zj")
@Api(value = "ZjController", tags = "专家管理")
@RestController
public class ZjController extends BaseController {
    @Autowired
    private ZjService zjService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return success(zjService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        return success(zjService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TZj tZj) {
        return save(zjService.save(tZj));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TZj tZj) {
        return update(zjService.updateById(tZj));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        return remove(zjService.removeById(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        return  remove(zjService.removeByIds(ids));
    }

}
