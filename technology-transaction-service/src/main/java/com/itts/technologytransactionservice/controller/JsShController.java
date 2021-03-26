package com.itts.technologytransactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsShService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核管理
 */

@RequestMapping(BASE_URL+"/v1/JsSh")
@Api(value = "JsShController", tags = "技术审核管理")
@RestController
public class JsShController extends BaseController {
    @Autowired
    private JsShService jsShService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsSh> tJsShIPage = jsShService.page(query);
        return success(tJsShIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        TJsSh tJsSh = jsShService.getById(id);
        return success(tJsSh);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsSh tJsSh) {
        boolean result = jsShService.save(tJsSh);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsSh tJsSh) {
        boolean result = jsShService.updateById(tJsSh);
        return update(result);
    }

    /**
     * 删除成果或需求
     */
    @GetMapping("/remove")
    public R remove(Long cgId,Long xqId) {
        boolean result = jsShService.deleteById(cgId,xqId);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = jsShService.removeByIds(ids);
        return  remove(result);
    }

}
