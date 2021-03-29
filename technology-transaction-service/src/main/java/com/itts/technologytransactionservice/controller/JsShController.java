package com.itts.technologytransactionservice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TJsSh;
import com.itts.technologytransactionservice.service.JsShService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.itts.common.constant.SystemConstant.BASE_URL;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核管理
 */

@RequestMapping(BASE_URL+"/v1/JsSh")
@Api(value = "JsShController", tags = "技术审核管理")
@RestController
public class JsShController extends BaseController {
    @Autowired
    private JsShService jsShService;

    /**
    * 分页查询
    * @param params
    * @return
    */
    @PostMapping("/page")
    public R page(@RequestBody Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        IPage<TJsSh> tJsShIPage = jsShService.page(query);
        return success(tJsShIPage);
    }

    /**
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") String id) {
        TJsSh tJsSh = jsShService.getById(id);
        return success(tJsSh);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TJsSh tJsSh) {
        boolean result = jsShService.save(tJsSh);
        return save(result);
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody TJsSh tJsSh) {
        boolean result = jsShService.updateById(tJsSh);
        return update(result);
    }

    /**
     * 删除成果或需求
     */
    @PutMapping("/remove")
    public R remove(@RequestParam(value = "cgId",required = false) Integer cgId,@RequestParam(value = "xqId",required = false)Integer xqId) {
        boolean result = jsShService.deleteById(cgId,xqId);
        return remove(result);
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        boolean result = jsShService.removeByIds(ids);
        return  remove(result);
    }

    /**
     * 发布审核成果(1待审核;2通过;3整改;4拒绝)
     * @param params
     * @return
     */
    @RequestMapping("/auditCg")
    public ResponseUtil auditCg(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());

        if (fbshzt != 2 && fbshzt != 3 && fbshzt != 4){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(jsShService.auditCg(params,fbshzt));
    }

    /**
     * 发布审核需求(1待审核;2通过;3整改;4拒绝)
     * @param params
     * @return
     */
    @RequestMapping("/auditXq")
    public ResponseUtil auditXq(@RequestBody Map<String, Object> params) {
        Integer fbshzt = Integer.parseInt(params.get("fbshzt").toString());
        if (fbshzt != 2 && fbshzt != 3 && fbshzt != 4){
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        return ResponseUtil.success(jsShService.auditXq(params,fbshzt));
    }

}
