package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.service.ITJsCgService;
import com.itts.technologytransactionservice.service.ITJsShService;
import io.swagger.annotations.Api;
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
 * @date 2021-02-22 09:16:14
 */
@Api(tags = "技术成果")
@RestController
@RequestMapping("/back/tJsCg")
public class TJsCgController extends BaseController {
    @Autowired
    private ITJsCgService tJsCgService;


    @Autowired
    private ITJsShService tJsShService;

    /**
     * (前台)分页条件查询
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil FindtJsCgByTJsLbTJsLy(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        PageInfo<TJsCg> page = tJsCgService.FindtJsCgByTJsLbTJsLy(query);
        return ResponseUtil.success(page);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(tJsCgService.getById(Integer.valueOf(id)));
    }
    /**
     * 根据成果名称查询
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public R getByName(@PathVariable("cgmc") String cgmc) {
        return success(tJsCgService.selectByName(cgmc));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsCg tJsCg) throws Exception {
        return save(tJsCgService.saveCg(tJsCg));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsCg tJsCg) {
        return update(tJsCgService.updateTJsCg(tJsCg));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(tJsCgService.removeByIdCg(Integer.valueOf(id)));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){

        return  remove(tJsCgService.removeByIdsCg(ids));
    }

    /**
     * 发布审核通过
     * @param id
     * @return
     */
    @RequestMapping("/pass/{id}")
    public R pass(@PathVariable("id") String id) {
        return update(tJsCgService.passUpdateById(Integer.valueOf(id)));
    }

    /**
     * 发布审核不通过并填写备注
     * @param params
     * @return
     */
    @PostMapping("/disPass")
    public R disPass(@RequestBody Map<String, Object> params) {
        return update(tJsCgService.disPassById(params));
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
        return  remove(tJsCgService.issueBatch(list));
    }

    /**
     * 已发布的成果申请拍卖招标(受理协办)
     * @param tJsCg
     * @return
     */
    @RequestMapping("/assistanceUpdate")
    public R assistanceUpdate(@RequestBody TJsCg tJsCg) {
        return update(tJsCgService.assistanceUpdateTJsCg(tJsCg));
    }

    /**
     * 受理协办审核
     * @param id
     * @return
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        return update(tJsCgService.assistancePassUpdateById(Integer.valueOf(id)));
    }

    /**
     * 受理协办审核不通过并填写备注
     * @param params
     * @return
     */
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        return update(tJsCgService.assistanceDisPassById(params));
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
        return  remove(tJsCgService.assistanceIssueBatch(list));
    }

}
