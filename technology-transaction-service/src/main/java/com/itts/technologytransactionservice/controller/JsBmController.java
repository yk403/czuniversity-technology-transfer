package com.itts.technologytransactionservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.mapper.JsHdMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.model.TJsLb;
import com.itts.technologytransactionservice.service.JsBmService;
import com.itts.technologytransactionservice.service.JsHdService;
import com.itts.technologytransactionservice.service.JsLbService;
import com.itts.technologytransactionservice.service.JsXtxxService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.UNCHECK_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(BASE_URL+"/v1/JsBm")
@Api(value = "JsBmController", tags = "活动报名管理")
@RestController
public class JsBmController extends BaseController {

    @Autowired
    private JsBmService jsBmService;
    @Autowired
    private JsHdMapper jsHdMapper;
    @Autowired
    private JsXtxxService jsXtxxService;
    @Autowired
    private JsHdService jsHdService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page/usr")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //params.put("userId",2);
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsBmService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsBmService.getById(Integer.parseInt(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsBm tJsBm) {

        return save(jsBmService.saveBm(tJsBm));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsBm tJsBm) {
        R update=update(jsBmService.updateById(tJsBm));
        TJsHd tJsHd=jsHdService.getById(tJsBm.getHdId());
        String  hdlx="";
        if(tJsHd.getHdlx()==0){
            hdlx="技术拍卖";
        }
        if(tJsHd.getHdlx()==1){
            hdlx="技术招标";
        }
        if(tJsHd.getHdlx()==2){
            hdlx="技术挂牌";
        }
        hdlx+="报名管理";
        if(tJsBm.getShzt().equals("2")){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsBm.getUserId().longValue(),0,hdlx,"","报名成功",jsHdService.getById(tJsBm.getHdId()).getHdmc());
        }
        if(tJsBm.getShzt().equals("1")){
            jsXtxxService.addXtxx(jsXtxxService.getUserId(),tJsBm.getUserId().longValue(),0,hdlx,"","报名拒绝",jsHdService.getById(tJsBm.getHdId()).getHdmc());
        }
        return update;
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsBmService.removeByIdBm(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){
        return  remove(jsBmService.removeByIdsBm(ids));
    }

}
