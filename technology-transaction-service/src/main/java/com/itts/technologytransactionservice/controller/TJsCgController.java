package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.service.ITJsCgService;
import com.itts.technologytransactionservice.service.ITJsShService;
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

@RestController
@RequestMapping("/back/tJsCg")
public class TJsCgController extends BaseController {
    @Autowired
    private ITJsCgService tJsCgService;


    @Autowired
    private ITJsShService tJsShService;
/*    *//**
    * 分页查询
    * @param params
    * @return
    *//*
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsCg> tJsCgIPage = tJsCgService.page(query);
        return success(tJsCgIPage);
    }*/


    /**
     * 分页条件查询
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
        long l = Long.parseLong(id);
        TJsCg tJsCg = tJsCgService.getById(l);
        return success(tJsCg);
    }
    /**
     * 根据cgmc查询
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public R getByName(@PathVariable("cgmc") String cgmc) {
        TJsCg tJsCg = tJsCgService.selectByName(cgmc);
        return success(tJsCg);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsCg tJsCg) throws Exception {
        boolean result = tJsCgService.saveCg(tJsCg);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsCg tJsCg) {
        boolean result = tJsCgService.updateTJsCg(tJsCg);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsCgService.removeByIdCg(l);
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
        boolean result = tJsCgService.removeByIds(longs);
        return  remove(result);
    }
    /*
    发布审核通过
     */
    @RequestMapping("/pass/{id}")
    public R pass(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsCgService.passUpdateById(l);
        return update(result);
    }
    /*
发布审核不通过并填写备注
 */
    @PostMapping("/disPass")
    public R disPass(@RequestBody Map<String, Object> params) {
        boolean result = tJsCgService.disPassById(params);
        return update(result);
    }
    /*
成果下发
 */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids){
        ArrayList<Long> longs = new ArrayList<>();
        for (String id: ids) {
            long l = Long.parseLong(id);
            longs.add(l);
        }
        boolean result = tJsCgService.issueBatch(longs);
        return  remove(result);
    }
    /*
    已发布的成果申请拍卖招标(受理协办)
     */
    @RequestMapping("/assistanceUpdate")
    public R assistanceUpdate(@RequestBody TJsCg tJsCg) {
        boolean result = tJsCgService.assistanceUpdateTJsCg(tJsCg);
        return update(result);
    }
    /*
    受理协办审核
 */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsCgService.assistancePassUpdateById(l);
        return update(result);
    }
    /*
受理协办审核不通过并填写备注
*/
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        boolean result = tJsCgService.assistanceDisPassById(params);
        return update(result);
    }
    /*
受理协办下发
*/
    @PostMapping("/assistanceIssueBatch")
    public R assistanceIssueBatch(@RequestBody List<String> ids){
        ArrayList<Long> longs = new ArrayList<>();
        for (String id: ids) {
            long l = Long.parseLong(id);
            longs.add(l);
        }
        boolean result = tJsCgService.assistanceIssueBatch(longs);
        return  remove(result);
    }


}
