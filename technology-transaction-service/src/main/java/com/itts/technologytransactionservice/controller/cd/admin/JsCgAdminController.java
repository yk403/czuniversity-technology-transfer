package com.itts.technologytransactionservice.controller.cd.admin;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.itts.common.enums.ErrorCodeEnum;
import com.itts.common.exception.WebException;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.service.cd.JsCgAdminService;
import com.itts.technologytransactionservice.service.cd.JsShAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术成果后台管理
 */
@RequestMapping(ADMIN_BASE_URL+"/v1/JsCg")
@Api(value = "JsCgAdminController", tags = "技术成果后台管理")
@RestController
public class JsCgAdminController extends BaseController {
    @Autowired
    private JsCgAdminService jsCgAdminService;


    @Autowired
    private JsShAdminService JsShAdminService;

    /**
     * 分页条件查询成果(后台管理)
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil findJsCg(@RequestBody Map<String, Object> params) {
        //前端传输标识type(0：审批管理;1：信息采集)
        if (params.get("type") == null) {
            Integer type = Integer.valueOf(params.get("type").toString());
            if (type != 0 && type != 1) {
                throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
            }
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //查询用户录入成功信息列表
        PageInfo<TJsCg> page = jsCgAdminService.findJsCg(params);
        return ResponseUtil.success(page);
    }

    /**
    * 根据成果id查询详细信息
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public ResponseUtil findById(@PathVariable("id") Integer id) {
        return ResponseUtil.success("查询成果详细信息成功!",jsCgAdminService.getById(id));
    }

    /**
     * 根据成果名称查询详细信息
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public ResponseUtil getByName(@PathVariable("cgmc") String cgmc) {
        return ResponseUtil.success(jsCgAdminService.selectByName(cgmc));
    }

    /**
     * 新增成果信息
     */
    @PostMapping("/save")
    public ResponseUtil save(@RequestBody TJsCg tJsCg,@RequestParam(value = "jylx",required = false)Integer jylx) {
        if (tJsCg.getId() != null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!jsCgAdminService.saveCg(tJsCg,jylx)) {
            return ResponseUtil.error(NAME_EXIST_ERROR);
        }
        return ResponseUtil.success("新增成果信息成功!");
    }

    /**
     * 修改成果信息
     */
    @PutMapping("/update")
    public ResponseUtil update(@RequestBody TJsCg tJsCg) {
        jsCgAdminService.updateTJsCg(tJsCg);
        return ResponseUtil.success("修改成果信息成功!");
    }

    /**
     * 根据成果id删除成果信息
     */
    @DeleteMapping("/remove/{id}")
    public ResponseUtil remove(@PathVariable("id") Integer id) {
        if (!jsCgAdminService.removeByCgId(id)) {
            throw new WebException(DELETE_ERROR);
        }
        return ResponseUtil.success("删除成果信息成功!");
    }

    /**
     * 根据id批量发布成果
     * @param ids
     * @return
     */
    @PutMapping("/issueBatch")
    public ResponseUtil issueBatch(@RequestBody List<Integer> ids){
        if (CollectionUtils.isEmpty(ids)) {
            throw new WebException(REQUEST_PARAMS_ISEMPTY);
        }
        if (!jsCgAdminService.issueBatch(ids)) {
            throw new WebException(ISSUE_BATCH_FAIL);
        }
        return ResponseUtil.success("批量发布成果成功!");
    }

    /**
     * 受理协办成果拍卖挂牌下发
     * @param ids
     * @return
     */
    @PostMapping("/assistanceIssueBatch")
    public ResponseUtil assistanceIssueBatch(@RequestBody List<Integer> ids){
        if (!jsCgAdminService.assistanceIssueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return  ResponseUtil.success("受理协办成果拍卖挂牌下发成功!");
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){

        return  remove(jsCgAdminService.removeByIdsCg(ids));
    }



}
