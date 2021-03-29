package com.itts.technologytransactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TQy;
import com.itts.technologytransactionservice.service.QyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 企业管理
 */

@RequestMapping(BASE_URL+"/v1/Qy")
@Api(value = "QyController", tags = "企业管理")
@RestController
public class QyController extends BaseController {
    @Autowired
    private QyService qyService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TQy> tQyIPage = qyService.page(query);
        return success(tQyIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        TQy tQy = qyService.getById(id);
        return success(tQy);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TQy tQy) {
        boolean result = qyService.save(tQy);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TQy tQy) {
        boolean result = qyService.updateById(tQy);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = qyService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = qyService.removeByIds(ids);
        return  remove(result);
    }

}
