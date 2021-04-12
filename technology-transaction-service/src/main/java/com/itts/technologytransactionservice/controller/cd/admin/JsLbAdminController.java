package com.itts.technologytransactionservice.controller.cd.admin;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.cd.JsLbAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;

/**
 * @Author: Austin
 * @Data: 2021/3/31
 * @Version: 1.0.0
 * @Description: 技术类别后台管理
 */
@RequestMapping(ADMIN_BASE_URL+"/v1/JsLb")
@Api(value = "JsLbAdminController", tags = "技术类别后台管理")
@RestController
public class JsLbAdminController extends BaseController {
    @Autowired
    private JsLbAdminService jsLbAdminService;

    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsLbAdminService.page(query));
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsLbAdminService.getById(Long.parseLong(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsLb tJsLb) {
        return save(jsLbAdminService.save(tJsLb));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsLb tJsLb) {
        return update(jsLbAdminService.updateById(tJsLb));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsLbAdminService.removeById(Long.parseLong(id)));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){
        ArrayList<Long> longs = new ArrayList<>();
        for (String id: ids) {
            longs.add(Long.parseLong(id));
        }
        return  remove(jsLbAdminService.removeByIds(longs));
    }
}
