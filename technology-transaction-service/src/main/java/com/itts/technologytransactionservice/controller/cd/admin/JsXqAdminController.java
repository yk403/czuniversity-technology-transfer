package com.itts.technologytransactionservice.controller.cd.admin;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.itts.common.bean.LoginUser;
import com.itts.common.constant.SystemConstant;
import com.itts.common.exception.ServiceException;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
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
import static com.itts.common.enums.ErrorCodeEnum.*;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求后台管理
 */

@RequestMapping(ADMIN_BASE_URL + "/v1/JsXq")
@Api(value = "JsXqAdminController", tags = "技术需求后台管理")
@RestController
public class JsXqAdminController extends BaseController {
    @Autowired
    private JsXqAdminService jsXqAdminService;

    /**
     * 分页条件查询需求(后台管理)
     *
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil findJsXq(@RequestBody Map<String, Object> params) {
        //前端传输标识type(0：审批管理;1：信息采集)
        return ResponseUtil.success(jsXqAdminService.findJsXq(params));
    }

    /**
     * 分页条件查询需求(前后台)
     *
     * @param params
     * @return
     */
    @PostMapping("/pageAll")
    public ResponseUtil findJsXqAll(@RequestBody Map<String, Object> params) {
        //前端传输标识type(0：审批管理;1：信息采集)
        return ResponseUtil.success(jsXqAdminService.findJsXq(params));
    }
    /**
     * 分页条件查询成果(归档清算用)
     *
     * @param params
     * @return
     */
    @PostMapping("/gdPage")
    public ResponseUtil gdPageJsXq(@RequestBody Map<String, Object> params) {
        //前端传输标识type(0：审批管理;1：信息采集)
        return ResponseUtil.success(jsXqAdminService.findGdJsXq(params));
    }

    /**
     * 根据需求id查询详细信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public ResponseUtil getById(@PathVariable("id") Integer id) {
        return ResponseUtil.success("查询需求详细信息成功!", jsXqAdminService.getById(id));
    }

    /**
     * 根据需求名称查询详细信息
     *
     * @param xqmc
     * @return
     */
    @GetMapping("/getByName/{xqmc}")
    public ResponseUtil getByName(@PathVariable("xqmc") String xqmc) {
        return ResponseUtil.success("查询需求详细信息成功!", jsXqAdminService.selectByName(xqmc));
    }

    /**
     * 新增需求信息
     */
    @PostMapping("/save")
    public ResponseUtil save(@RequestBody TJsXq tJsXq) {
        if (tJsXq.getId() != null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!jsXqAdminService.saveXq(tJsXq)) {
            return ResponseUtil.error(NAME_EXIST_ERROR);
        }
        return ResponseUtil.success("添加成功!");
    }

    /**
     * 修改需求信息
     */
    @PutMapping("/update")
    public ResponseUtil update(@RequestBody TJsXq tJsXq) {
        jsXqAdminService.updateTJsXq(tJsXq);
        return ResponseUtil.success("修改需求信息成功!");
    }

    /**
     * 根据需求id删除需求信息
     */
    @GetMapping("/remove/{id}")
    public ResponseUtil remove(@PathVariable("id") Integer id) {
        if (!jsXqAdminService.removeByXqId(id)) {
            throw new WebException(DELETE_ERROR);
        }
        return ResponseUtil.success("删除需求信息成功!");
    }

    /**
     * 根据id批量发布需求
     *
     * @param ids
     * @return
     */
    @PutMapping("/issueBatch")
    public ResponseUtil issueBatch(@RequestBody List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new WebException(REQUEST_PARAMS_ISEMPTY);
        }
        if (!jsXqAdminService.issueBatch(ids)) {
            throw new WebException(ISSUE_BATCH_FAIL);
        }
        return ResponseUtil.success("批量发布需求成功!");
    }

    /**
     * 受理协办需求招标下发
     */
    @PostMapping("/assistanceIssueBatch")
    public ResponseUtil assistanceIssueBatch(@RequestBody List<Integer> ids) {
        if (!jsXqAdminService.assistanceIssueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("下发成功!");
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
        if(params.get("fjjgId")==null){
            LoginUser loginUser = SystemConstant.threadLocal.get();
            Long fjjgId = loginUser.getJgId();
            params.put("fjjgId",fjjgId);
        }
        Query query = new Query(params);
        return ResponseUtil.success(jsXqAdminService.PageByTJsFb(query));
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
        return remove(jsXqAdminService.removeByIds(longs));
    }
    /**
     * 成果上移下移
     * @param
     * @return
     */
    @GetMapping("/xqmove")
    public ResponseUtil xqmove(@RequestParam(value = "id") Integer id,@RequestParam(value = "type") Integer type){

        jsXqAdminService.xqmove(id,type);
        return ResponseUtil.success("成果移动成功!");
    }
    /**
     * 成果置顶置底 置顶ype为0 置底type为1
     * @param
     * @return
     */
    @GetMapping("/topBottom")
    public ResponseUtil topBottom(@RequestParam(value = "id") Integer id,@RequestParam(value = "type") Integer type){
        jsXqAdminService.topBottom(id,type);
        return ResponseUtil.success("成果置顶置底成功!");
    }
}
