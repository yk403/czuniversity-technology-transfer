package com.itts.technologytransactionservice.controller;


import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.test.bid.BidController;
import com.itts.technologytransactionservice.mapper.JsBmMapper;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsCjRc;
import com.itts.technologytransactionservice.model.TJsCjRcDto;
import com.itts.technologytransactionservice.service.JsBmService;
import com.itts.technologytransactionservice.service.JsCjRcService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术类别管理
 */

@RequestMapping(BASE_URL+"/v1/JsCjRc")
@Api(value = "JsCjRcController", tags = "受让方出价记录表")
@RestController
public class JsCjRcController extends BaseController {
    @Autowired
    private BidController bidController;
    @Autowired
    private JsCjRcService jsCjRcService;

    /**
    *websocket监听程序
     */
    private void listenData() throws WebException, IOException {
        bidController.sendMessage("刷新页面");
    }
    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsCjRcService.page(query));
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        return success(jsCjRcService.getById(new BigInteger(id)));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseUtil save(@RequestBody TJsCjRcDto tJsCjRcDto) throws IOException {
        if(jsCjRcService.saveCjRc(tJsCjRcDto)){
            bidController.onMessage("保存成功，调用刷新页面方法");
            return ResponseUtil.success("保存成功");
        }else{
            return ResponseUtil.error(400,"错误");
        }


    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsCjRc tJsCjRc) {
        return update(jsCjRcService.updateById(tJsCjRc));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return remove(jsCjRcService.removeByIdCjRc(id));
    }



}
