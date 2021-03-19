package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.ITJsShService;
import com.itts.technologytransactionservice.service.ITJsXqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */

@RestController
@RequestMapping("/back/tJsXq")
public class TJsXqController extends BaseController {
    @Autowired
    private ITJsXqService tJsXqService;

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
        IPage<TJsXq> tJsXqIPage = tJsXqService.page(query);
        return success(tJsXqIPage);
    }*/
    /**
     * 分页条件查询
     * @param params
     * @return
     */
    @PostMapping("/FindTJsXqByTJsLbTJsLy")
    public R FindTJsXqByTJsLbTJsLy(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        IPage<TJsXq> tJsXqIPage = tJsXqService.FindTJsXqByTJsLbTJsLy(query);
        return success(tJsXqIPage);
    }
    /**
     * 分页条件查询
     * @param params
     * @return
     */
    @PostMapping("/PageByTJsFb")
    public R PageByTJsFb(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        IPage<TJsFb> tJsFbIPage = tJsXqService.PageByTJsFb(query);
        return success(tJsFbIPage);
    }
    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        TJsXq tJsXq = tJsXqService.getById(l);
        return success(tJsXq);
    }
    /**
     * 根据Name查询
     * @param xqmc
     * @return
     */
    @GetMapping("/getByName/{xqmc}")
    public R getByName(@PathVariable("xqmc") String xqmc) {
        TJsXq tJsXq = tJsXqService.selectByName(xqmc);
        return success(tJsXq);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional
    public R save(@RequestBody TJsXq tJsXq) throws Exception {
        boolean result = tJsXqService.saveXq(tJsXq);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsXq tJsXq) {
        boolean result = tJsXqService.updateTJsXq(tJsXq);
        return update(result);
    }
    /*

     */

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsXqService.removeByIdXq(l);
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
        boolean result = tJsXqService.removeByIds(longs);
        return  remove(result);
    }
    /*
    发布审核通过
     */
    @RequestMapping("/pass/{id}")
    public R pass(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsXqService.passUpdateById(l);
        return update(result);
    }
    /*
    发布审核不通过并填写备注
     */
    @PostMapping("/disPass")
    public R disPass(@RequestBody Map<String, Object> params) {
        boolean result = tJsXqService.disPassById(params);
        return update(result);
    }
    /*
    需求发布下发
     */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids){
        ArrayList<Long> longs = new ArrayList<>();
        for (String id: ids) {
            long l = Long.parseLong(id);
            longs.add(l);
        }
        boolean result = tJsXqService.issueBatch(longs);
        return  remove(result);
    }
    /*
        受理协办审核
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        long l = Long.parseLong(id);
        boolean result = tJsXqService.assistancePassUpdateById(l);
        return update(result);
    }
        /*
        受理协办审核不通过并填写备注
     */
        @PostMapping("/assistanceDisPass")
        public R assistanceDisPass(@RequestBody Map<String, Object> params) {
            boolean result = tJsXqService.assistanceDisPassById(params);
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
            boolean result = tJsXqService.assistanceIssueBatch(longs);
            return  remove(result);
        }
            /*
    已发布的需求申请挂牌(受理协办)
     */
            @RequestMapping("/assistanceUpdate")
            public R assistanceUpdate(@RequestBody TJsXq tJsXq) {
                boolean result = tJsXqService.assistanceUpdateTJsXq(tJsXq);
                return update(result);
            }



}
