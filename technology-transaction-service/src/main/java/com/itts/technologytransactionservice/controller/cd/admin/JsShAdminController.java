package com.itts.technologytransactionservice.controller.cd.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.AUDIT_MSG_ISEMPTY;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核后台管理
 */

@RequestMapping(ADMIN_BASE_URL + "/v1/JsSh")
@Api(value = "JsShController", tags = "技术审核后台管理")
@RestController
public class JsShAdminController extends BaseController {
    @Autowired
    private JsShAdminService JsShAdminService;

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        if(params.get("fjjgId")==null){
            LoginUser loginUser = SystemConstant.threadLocal.get();
            Long fjjgId = loginUser.getFjjgId();
            params.put("fjjgId",fjjgId);
        }
        Query query = new Query(params);
        return success(JsShAdminService.page(query));
    }

    @GetMapping("/jsUpdate")
    @ApiOperation(value = "归档清算结算逻辑")
    public ResponseUtil updateByCgXqId(@RequestParam(value = "cgId", required = false)Integer cgId,
                                @RequestParam(value = "xqId", required = false)Integer xqId) {
        if(cgId!=null){
            JsShAdminService.updateByCgId(cgId);
        }
        if(xqId!=null){
            JsShAdminService.updateByXqId(xqId);
        }
        return ResponseUtil.success();
    }
    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(JsShAdminService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsSh tJsSh) {
        return save(JsShAdminService.save(tJsSh));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsSh tJsSh) {
        return update(JsShAdminService.updateById(tJsSh));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Long id) {
        return remove(JsShAdminService.removeById(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids) {
        return remove(JsShAdminService.removeByIds(ids));
    }

    /**
     * 发布审核成果(1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @RequestMapping("/auditCg")
    public ResponseUtil auditCg(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
        if (fbshzt != 2 && fbshzt != 3 && fbshzt != 4) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!JsShAdminService.auditCg(params, fbshzt)) {
            throw new WebException(AUDIT_MSG_ISEMPTY);
        }
        return ResponseUtil.success("审核成果完成!");
    }

    /**
     * 发布审核需求(1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @RequestMapping("/auditXq")
    public ResponseUtil auditXq(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
        if (fbshzt != 2 && fbshzt != 3 && fbshzt != 4) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!JsShAdminService.auditXq(params, fbshzt)) {
            throw new WebException(AUDIT_MSG_ISEMPTY);
        }
        return ResponseUtil.success("审核需求完成!");
    }

    /**
     * 招标审核需求(1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @RequestMapping("/assistanceAuditXq")
    public ResponseUtil assistanceAuditXq(@RequestBody Map<String, Object> params) {
        Integer assistanceStatus = Integer.parseInt(params.get("assistanceStatus").toString());
        if (assistanceStatus != 2 && assistanceStatus != 3 && assistanceStatus != 4) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!JsShAdminService.assistanceAuditXq(params, assistanceStatus)) {
            throw new WebException(AUDIT_MSG_ISEMPTY);
        }
        return ResponseUtil.success("审核需求完成!");
    }

    /**
     * 拍卖挂牌审核成果(1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    @RequestMapping("/assistanceAuditCg")
    public ResponseUtil assistanceAuditCg(@RequestBody Map<String, Object> params) {
        Integer assistanceStatus = Integer.parseInt(params.get("assistanceStatus").toString());
        if (assistanceStatus != 2 && assistanceStatus != 3 && assistanceStatus != 4) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!JsShAdminService.assistanceAuditCg(params, assistanceStatus)) {
            throw new WebException(AUDIT_MSG_ISEMPTY);
        }
        return ResponseUtil.success("审核成果完成!");
    }

}
