package com.itts.technologytransactionservice.controller.cd.admin;


import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.service.JsBmService;
import com.itts.technologytransactionservice.service.JsXtxxService;
import com.itts.technologytransactionservice.service.cd.JsBmAdminService;
import com.itts.technologytransactionservice.service.cd.JsHdAdminService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.ADMIN_BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.*;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/JsBm")
@Api(value = "JsBmAdminController", tags = "活动报名后台管理")
@RestController
public class JsBmAdminController extends BaseController {

    @Autowired
    private JsBmAdminService jsBmAdminService;
    @Autowired
    private JsXtxxService jsXtxxService;
    @Autowired
    private JsHdAdminService jsHdAdminService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsBmAdminService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsBmAdminService.getById(Integer.parseInt(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsBm tJsBm) {
        return save(jsBmAdminService.save(tJsBm));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsBm tJsBm) {
        Integer id = tJsBm.getId();
        //检查参数是否合法
        if (id == null ) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        //检查数据库中是否存在要更新的数据
        if (jsBmAdminService.getById(id) == null) {
            throw new WebException(SYSTEM_NOT_FIND_ERROR);
        }
        //更新数据
        if (!jsBmAdminService.updateById(tJsBm)) {
            throw new WebException(UPDATE_FAIL);
        }
        TJsBm byId=jsBmAdminService.getById(id);
        if(tJsBm.getShzt().equals("2")){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),byId.getUserId().longValue(),5,0,jsHdAdminService.getById(byId.getHdId()).getHdmc());
        }
        if(tJsBm.getShzt().equals("1")){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),byId.getUserId().longValue(),5,1,jsHdAdminService.getById(byId.getHdId()).getHdmc());
        }
        return update(true);
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsBmAdminService.removeByIdBm(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){
        return  remove(jsBmAdminService.removeByIdsBm(ids));
    }

}
