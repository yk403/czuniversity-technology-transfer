package com.itts.technologytransactionservice.controller.cd.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/tJsSh")
@Api(value = "JsShController", tags = "技术审核管理")
@RestController
public class JsShAdminController extends BaseController {
    @Autowired
    private JsShAdminService JsShAdminService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsSh> tJsShIPage = JsShAdminService.page(query);
        return success(tJsShIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        TJsSh tJsSh = JsShAdminService.getById(id);
        return success(tJsSh);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsSh tJsSh) {
        boolean result = JsShAdminService.save(tJsSh);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsSh tJsSh) {
        boolean result = JsShAdminService.updateById(tJsSh);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        boolean result = JsShAdminService.removeById(id);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = JsShAdminService.removeByIds(ids);
        return  remove(result);
    }

}
