package com.itts.technologytransactionservice.controller;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.JsHdService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.constant.SystemConstant.UNCHECK_BASE_URL;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术活动管理
 */

@RequestMapping(BASE_URL + "/v1/JsHd")
@Api(value = "JsHdController", tags = "技术活动管理")
@RestController
public class JsHdController extends BaseController {
    @Autowired
    private JsHdService jsHdService;

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil page(@RequestBody Map<String, Object> params) {

        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsHdService.page(query));
    }
    /**
     * 分页查询(前台交易大厅查询)
     * @param params
     * @return
     */
    @PostMapping("/pageFront1")
    public ResponseUtil pageFront1(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        return ResponseUtil.success(jsHdService.pageFront1(query));
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        return success(jsHdService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsHd tJsHd) {

        return save(jsHdService.save(tJsHd));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody TJsHd tJsHd) {
        return update(jsHdService.updateById(tJsHd));
    }

    /**
     * 删除
     */
    @GetMapping("/remove/{id}")
    public R remove(@PathVariable("id") Integer id) {
        return remove(jsHdService.removeByIdHd(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids) {
        return remove(jsHdService.removeByIds(ids));
    }
    /*
    交易大厅初始化获取服务器当前时间
     */
    @GetMapping("/getCurrentTime")
    public ResponseUtil getCurrentTime(){
        TJsHd tJsHd=new TJsHd();
        tJsHd.setHddqsj(new Date());
        return ResponseUtil.success(tJsHd.getHddqsj());
    }
}
