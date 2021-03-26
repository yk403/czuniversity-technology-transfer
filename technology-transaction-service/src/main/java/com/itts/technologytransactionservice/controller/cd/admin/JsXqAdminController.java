package com.itts.technologytransactionservice.controller.cd.admin;


import com.github.pagehelper.PageInfo;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsFb;
import com.itts.technologytransactionservice.model.TJsXq;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/tJsXq")
@Api(value = "JsShController", tags = "技术需求管理")
@RestController
public class JsXqAdminController extends BaseController {
    @Autowired
    private JsXqAdminService JsXqAdminService;

    @Autowired
    private JsShAdminService JsShAdminService;

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
        PageInfo<TJsXq> page = JsXqAdminService.FindTJsXqByTJsLbTJsLy(query);
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
        PageInfo<TJsFb> page = JsXqAdminService.PageByTJsFb(query);
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
        TJsXq tJsXq = JsXqAdminService.selectById(Integer.valueOf(id));
        return success(tJsXq);
    }

    /**
     * 根据Name查询
     * @param xqmc
     * @return
     */
    @GetMapping("/getByName/{xqmc}")
    public R getByName(@PathVariable("xqmc") String xqmc) {
        TJsXq tJsXq = JsXqAdminService.selectByName(xqmc);
        return success(tJsXq);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional
    public R save(@RequestBody TJsXq tJsXq) throws Exception {
        boolean result = JsXqAdminService.saveXq(tJsXq);
        return save(result);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsXq tJsXq) {
        boolean result = JsXqAdminService.updateTJsXq(tJsXq);
        return update(result);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove( JsXqAdminService.removeByIdXq(Integer.valueOf(id)));
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
        boolean result = JsXqAdminService.removeByIds(longs);
        return remove(result);
    }

    /**
     * 发布审核通过
     * @param id
     * @return
     */
    @RequestMapping("/pass/{id}")
    public R pass(@PathVariable("id") String id) {
        return update(JsXqAdminService.passUpdateById(Integer.valueOf(id)));
    }

    /**
     * 发布审核不通过并填写备注
     */
    @PostMapping("/disPass")
    public R disPass(@RequestBody Map<String, Object> params) {
        return update(JsXqAdminService.disPassById(params));
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
        return remove(JsXqAdminService.issueBatch(list));
    }

    /**
     * 受理协办审核
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        return update(JsXqAdminService.assistancePassUpdateById(Integer.valueOf(id)));
    }

    /**
     * 受理协办审核不通过并填写备注
     */
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        boolean result = JsXqAdminService.assistanceDisPassById(params);
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
        boolean result = JsXqAdminService.assistanceIssueBatch(list);
        return remove(result);
    }

    /**
     * 已发布的需求申请挂牌(受理协办)
     */
    @RequestMapping("/assistanceUpdate")
    public R assistanceUpdate(@RequestBody TJsXq tJsXq) {
        boolean result = JsXqAdminService.assistanceUpdateTJsXq(tJsXq);
        return update(result);
    }

}
