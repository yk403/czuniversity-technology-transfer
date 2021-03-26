package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.service.JsCgService;
import com.itts.technologytransactionservice.service.JsShService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果管理
 */

@RequestMapping(BASE_URL+"/v1/JsCg")
@Api(value = "JsCgController", tags = "技术成果管理")
@RestController
public class JsCgController extends BaseController {
    @Autowired
    private JsCgService jsCgService;


    @Autowired
    private JsShService jsShService;

    /**
     * (前台)分页条件查询
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil FindJsCgByJsLbJsLy(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        PageInfo<TJsCg> page = jsCgService.FindtJsCgByTJsLbTJsLy(query);
        return ResponseUtil.success(page);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsCgService.getById(Integer.valueOf(id)));
    }
    /**
     * 根据成果名称查询
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public R getByName(@PathVariable("cgmc") String cgmc) {
        return success(jsCgService.selectByName(cgmc));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsCg tJsCg) throws Exception {
        return save(jsCgService.saveCg(tJsCg));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsCg tJsCg) {
        return update(jsCgService.updateTJsCg(tJsCg));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsCgService.removeByIdCg(Integer.valueOf(id)));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){

        return  remove(jsCgService.removeByIdsCg(ids));
    }

    /**
     * 发布审核通过
     * @param id
     * @return
     */
    @RequestMapping("/pass/{id}")
    public R pass(@PathVariable("id") String id) {
        return update(jsCgService.passUpdateById(Integer.valueOf(id)));
    }

    /**
     * 发布审核不通过并填写备注
     * @param params
     * @return
     */
    @PostMapping("/disPass")
    public R disPass(@RequestBody Map<String, Object> params) {
        return update(jsCgService.disPassById(params));
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
        return  remove(jsCgService.issueBatch(list));
    }

    /**
     * 已发布的成果申请拍卖招标(受理协办)
     * @param tJsCg
     * @return
     */
    @RequestMapping("/assistanceUpdate")
    public R assistanceUpdate(@RequestBody TJsCg tJsCg) {
        return update(jsCgService.assistanceUpdateTJsCg(tJsCg));
    }

    /**
     * 受理协办审核
     * @param id
     * @return
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        return update(jsCgService.assistancePassUpdateById(Integer.valueOf(id)));
    }

    /**
     * 受理协办审核不通过并填写备注
     * @param params
     * @return
     */
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        return update(jsCgService.assistanceDisPassById(params));
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
        return  remove(jsCgService.assistanceIssueBatch(list));
    }

}
