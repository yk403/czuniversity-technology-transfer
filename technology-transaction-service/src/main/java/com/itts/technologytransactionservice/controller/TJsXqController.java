package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.ITJsShService;
import com.itts.technologytransactionservice.service.ITJsXqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;


/**
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

    /**
     * (后台)分页条件查询
     *
     * @param params
     * @return
     */
    @PostMapping("/FindTJsXqByTJsLbTJsLy")
    public ResponseUtil FindTJsXqByTJsLbTJsLy(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        PageInfo<TJsXq> page = tJsXqService.FindTJsXqByTJsLbTJsLy(query);
        return ResponseUtil.success(page);
    }

    /**
     * 分页条件查询
     *
     * @param params
     * @return
     */
    @PostMapping("/PageByTJsFb")
    public ResponseUtil PageByTJsFb(@RequestBody Map<String, Object> params) {
        //查询邻域类别审核状态列表数据
        Query query = new Query(params);
        PageInfo<TJsFb> page = tJsXqService.PageByTJsFb(query);
        return ResponseUtil.success(page);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        if (StringUtils.isEmpty(id)) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        TJsXq tJsXq = tJsXqService.selectById(Integer.valueOf(id));
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

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove( tJsXqService.removeByIdXq(Integer.valueOf(id)));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids) {
        ArrayList<Long> longs = new ArrayList<>();
        for (String id :
                ids) {
            long l = Long.parseLong(id);
            longs.add(l);
        }
        boolean result = tJsXqService.removeByIds(longs);
        return remove(result);
    }

    /**
     * 发布审核通过
     * @param id
     * @return
     */
    @RequestMapping("/pass/{id}")
    public R pass(@PathVariable("id") String id) {
        return update(tJsXqService.passUpdateById(Integer.valueOf(id)));
    }

    /**
     * 发布审核不通过并填写备注
     */
    @PostMapping("/disPass")
    public R disPass(@RequestBody Map<String, Object> params) {
        return update(tJsXqService.disPassById(params));
    }

    /**
     * 需求发布下发
     */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return remove(tJsXqService.issueBatch(list));
    }

    /**
     * 受理协办审核
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        return update(tJsXqService.assistancePassUpdateById(Integer.valueOf(id)));
    }

    /**
     * 受理协办审核不通过并填写备注
     */
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        boolean result = tJsXqService.assistanceDisPassById(params);
        return update(result);
    }

    /**
     * 受理协办下发
     */
    @PostMapping("/assistanceIssueBatch")
    public R assistanceIssueBatch(@RequestBody List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        boolean result = tJsXqService.assistanceIssueBatch(list);
        return remove(result);
    }

    /**
     * 已发布的需求申请挂牌(受理协办)
     */
    @RequestMapping("/assistanceUpdate")
    public R assistanceUpdate(@RequestBody TJsXq tJsXq) {
        boolean result = tJsXqService.assistanceUpdateTJsXq(tJsXq);
        return update(result);
    }

}
