package com.itts.technologytransactionservice.controller.cd.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果管理
 */
@RequestMapping(ADMIN_BASE_URL+"/v1/tJsCg")
@Api(value = "JsCgController", tags = "技术成果管理")
@RestController
public class JsCgAdminController extends BaseController {
    @Autowired
    private JsCgAdminService JsCgAdminService;


    @Autowired
    private JsShAdminService JsShAdminService;

    /**
     * 分页条件查询
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil FindJsCgByJsLbJsLy(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        PageInfo<TJsCg> page = JsCgAdminService.FindtJsCgByTJsLbTJsLy(new Query(params));
        return ResponseUtil.success(page);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(JsCgAdminService.getById(Integer.valueOf(id)));
    }
    /**
     * 根据成果名称查询
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public R getByName(@PathVariable("cgmc") String cgmc) {
        return success(JsCgAdminService.selectByName(cgmc));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsCg tJsCg) throws Exception {
        return save(JsCgAdminService.saveCg(tJsCg));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsCg tJsCg) {
        return update(JsCgAdminService.updateTJsCg(tJsCg));
    }


    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){

        return  remove(JsCgAdminService.removeByIdsCg(ids));
    }

    /**
     * 成果下发
     * @param ids
     * @return
     */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids){
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return  remove(JsCgAdminService.issueBatch(list));
    }



    /**
     * 受理协办下发
     * @param ids
     * @return
     */
    @PostMapping("/assistanceIssueBatch")
    public R assistanceIssueBatch(@RequestBody List<String> ids){
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return  remove(JsCgAdminService.assistanceIssueBatch(list));
    }

}
