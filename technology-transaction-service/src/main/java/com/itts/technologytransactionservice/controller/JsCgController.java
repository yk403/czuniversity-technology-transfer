package com.itts.technologytransactionservice.controller;


import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.service.JsCgService;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果管理
 */

@RequestMapping(BASE_URL + "/v1/JsCg")
@Api(value = "JsCgController", tags = "技术成果管理")
@RestController
public class JsCgController extends BaseController {
    @Autowired
    private JsCgService jsCgService;

    @Autowired
    private JsCgAdminService jsCgAdminService;

    /**
     * 分页条件查询成果(前台)
     *
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil findJsCgFront(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsCgService.findJsCgFront(params));
    }

    /**
     * 分页条件查询成果(个人详情)(type: 0 采集 type: 1 发布 type:2 招拍挂)
     *
     * @param params
     * @return
     */
    @PostMapping("/page/user")
    public ResponseUtil findJsCg(@RequestBody Map<String, Object> params) {
        return ResponseUtil.success(jsCgService.findJsCgUser(params));
    }

    /**
     * 根据ID查询成果信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsCgAdminService.getById(Integer.valueOf(id)));
    }

    /**
     * 根据成果名称查询
     *
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public R getByName(@PathVariable("cgmc") String cgmc) {
        return success(jsCgService.selectByName(cgmc));
    }

    /**
     * 新增成果信息
     *
     * @param tJsCg
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsCg tJsCg) {
        return save(jsCgService.saveCg(tJsCg));
    }

    /**
     * 修改成果信息
     *
     * @param tJsCg
     * @return
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsCg tJsCg) {
        return update(jsCgService.updateTJsCg(tJsCg));
    }

    /**
     * 删除成果信息
     *
     * @param id
     * @return
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsCgService.removeByIdCg(Integer.valueOf(id)));
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids) {
        return remove(jsCgService.removeByIdsCg(ids));
    }

    /**
     * 已发布的成果申请拍卖挂牌(受理协办)
     *
     * @param params
     * @return
     */
    @PutMapping("/assistanceUpdate")
    public ResponseUtil assistanceUpdate(@RequestBody Map<String, Object> params) {
        if (params.get("jylx") == null) {
            throw new WebException(REQUEST_PARAMS_ISEMPTY);
        }
        Integer jylx = Integer.valueOf(params.get("jylx").toString());
        if (jylx != 0 && jylx != 2) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!jsCgService.assistanceUpdateTJsCg(params, jylx)) {
            throw new WebException(MSG_AUDIT_FAIL);
        }
        return ResponseUtil.success("成果申请拍卖挂牌!");
    }

    /**
     * 个人发布审核成果申请(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @PutMapping("/auditCg")
    public ResponseUtil auditCg(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
        if (fbshzt != 1) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success("申请审核成功", jsCgService.auditCg(params, fbshzt));
    }

    /**
     * 成果下发
     *
     * @param ids
     * @return
     */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return update(jsCgService.issueBatch(list));
    }


    /**
     * 受理协办审核
     *
     * @param id
     * @return
     */
    @RequestMapping("/assistancePass/{id}")
    public R assistancePass(@PathVariable("id") String id) {
        return update(jsCgService.assistancePassUpdateById(Integer.valueOf(id)));
    }

    /**
     * 受理协办审核不通过并填写备注
     *
     * @param params
     * @return
     */
    @PostMapping("/assistanceDisPass")
    public R assistanceDisPass(@RequestBody Map<String, Object> params) {
        return update(jsCgService.assistanceDisPassById(params));
    }

    /**
     * 受理协办下发
     *
     * @param ids
     * @return
     */
    @PostMapping("/assistanceIssueBatch")
    public R assistanceIssueBatch(@RequestBody List<String> ids) {
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return remove(jsCgService.assistanceIssueBatch(list));
    }
}
