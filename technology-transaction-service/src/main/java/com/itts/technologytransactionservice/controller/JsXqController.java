package com.itts.technologytransactionservice.controller;


import com.github.pagehelper.PageInfo;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.JsShService;
import com.itts.technologytransactionservice.service.JsXqService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求管理
 */

@RequestMapping(BASE_URL+"/v1/JsXq")
@Api(value = "JsXqController", tags = "技术需求管理")
@RestController
public class JsXqController extends BaseController {
    @Autowired
    private JsXqService jsXqService;

    @Autowired
    private JsShService jsShService;

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
        PageInfo<TJsXq> page = jsXqService.FindTJsXqByTJsLbTJsLy(query);
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
        PageInfo<TJsFb> page = jsXqService.PageByTJsFb(query);
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
        TJsXq tJsXq = jsXqService.selectById(Integer.valueOf(id));
        return success(tJsXq);
    }

    /**
     * 根据Name查询
     * @param xqmc
     * @return
     */
    @GetMapping("/getByName/{xqmc}")
    public R getByName(@PathVariable("xqmc") String xqmc) {
        TJsXq tJsXq = jsXqService.selectByName(xqmc);
        return success(tJsXq);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional
    public R save(@RequestBody TJsXq tJsXq) throws Exception {
        boolean result = jsXqService.saveXq(tJsXq);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsXq tJsXq) {
        boolean result = jsXqService.updateTJsXq(tJsXq);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove( jsXqService.removeByIdXq(Integer.valueOf(id)));
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
        boolean result = jsXqService.removeByIds(longs);
        return remove(result);
    }

    /**
     * 需求批量下发
     */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return update(jsXqService.issueBatch(list));
    }

    /**
     * 受理协办审核
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        return update(jsXqService.assistancePassUpdateById(Integer.valueOf(id)));
    }

    /**
     * 受理协办审核不通过并填写备注
     */
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        boolean result = jsXqService.assistanceDisPassById(params);
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
        boolean result = jsXqService.assistanceIssueBatch(list);
        return remove(result);
    }

    /**
     * 已发布的需求申请挂牌(受理协办)
     */
    @RequestMapping("/assistanceUpdate")
    public R assistanceUpdate(@RequestBody TJsXq tJsXq) {
        boolean result = jsXqService.assistanceUpdateTJsXq(tJsXq);
        return update(result);
    }

}
