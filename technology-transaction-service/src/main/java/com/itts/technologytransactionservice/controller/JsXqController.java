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
import com.itts.technologytransactionservice.service.cd.JsXqAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求管理
 */

@RequestMapping(BASE_URL + "/v1/JsXq")
@Api(value = "JsXqController", tags = "技术需求管理")
@RestController
public class JsXqController extends BaseController {
    @Autowired
    private JsXqService jsXqService;
    @Autowired
    private JsXqAdminService jsXqAdminService;

    /**
     * 分页条件查询需求(前台)
     *
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil findJsXqFront(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsXqService.findJsXqFront(params));
    }

    /**
     * 分页条件查询成果(个人详情)(type: 0 采集 type: 1 发布 type:2 招拍挂)
     *
     * @param params
     * @return
     */
    @PostMapping("/page/user")
    public ResponseUtil findJsXq(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsXqService.findJsXqUser(params));
    }


    /**
     * 根据ID查询需求信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsXqAdminService.getById(Integer.valueOf(id)));
    }

    /**
     * 根据Name查询
     *
     * @param xqmc
     * @return
     */
    @GetMapping("/getByName/{xqmc}")
    public R getByName(@PathVariable("xqmc") String xqmc) {
        return success(jsXqService.selectByName(xqmc));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Transactional
    public R save(@RequestBody TJsXq tJsXq) throws Exception {
        return save(jsXqService.saveXq(tJsXq));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsXq tJsXq) {
        return update(jsXqService.updateTJsXq(tJsXq));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsXqService.removeByIdXq(Integer.valueOf(id)));
    }

    /**
     * 已发布的需求招标申请(受理协办)
     *
     * @param params
     * @return
     */
    @PutMapping("/assistanceUpdate")
    public ResponseUtil assistanceUpdate(@RequestBody Map<String, Object> params) {
        if (params.get("jylx") == null || params.get("id") == null || params.get("xqxq") == null || params.get("jszb") == null || params.get("remarks") == null) {
            throw new WebException(REQUEST_PARAMS_ISEMPTY);
        }
        Integer jylx = Integer.valueOf(params.get("jylx").toString());
        if (jylx != 1) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!jsXqService.assistanceUpdateTJsXq(params, jylx)) {
            throw new WebException(MSG_AUDIT_FAIL);
        }
        return ResponseUtil.success("需求招标申请!");
    }

    /**
     * 个人发布审核需求申请(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @PutMapping("/auditXq")
    public ResponseUtil auditXq(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
        if (fbshzt != 1) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(jsXqService.auditXq(params, fbshzt));
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
        return ResponseUtil.success(jsXqService.PageByTJsFb(query));
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids) {
        ArrayList<Long> longs = new ArrayList<>();
        for (String id : ids) {
            longs.add(Long.parseLong(id));
        }
        return remove(jsXqService.removeByIds(longs));
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
        return update(jsXqService.assistanceDisPassById(params));
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
        return remove(jsXqService.assistanceIssueBatch(list));
    }

}
