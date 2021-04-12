package com.itts.technologytransactionservice.controller.cd.admin;

import com.github.pagehelper.PageInfo;
import com.itts.common.exception.WebException;
import com.itts.common.utils.Query;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.technologytransactionservice.controller.BaseController;
import com.itts.technologytransactionservice.model.JsHdDTO;
import com.itts.technologytransactionservice.model.TJsHd;
import com.itts.technologytransactionservice.service.JsHdService;
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
 * @Description: 技术活动后台管理
 */

@RequestMapping(ADMIN_BASE_URL+"/v1/JsHd")
@Api(value = "JsHdAdminController", tags = "技术活动后台管理")
@RestController
public class JsHdAdminController extends BaseController {
    @Autowired
    private JsHdService jsHdService;

    /**
    * 分页查询
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
    * 根据ID查询
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public R getById(@PathVariable("id") Long id) {
        return success(jsHdService.getById(id));
    }

    /**
     * 新增技术活动
     */
    @PostMapping("/save")
    public ResponseUtil save(@RequestBody JsHdDTO jsHdDTO) {
        if (jsHdDTO.getHdlx() != 0 && jsHdDTO.getHdlx() != 1 && jsHdDTO.getHdlx() != 2) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!jsHdService.add(jsHdDTO)) {
            throw new WebException(INSERT_FAIL);
        }
        return ResponseUtil.success("新增技术活动成功!");
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
    public R remove(@PathVariable("id") Long id) {
        return remove(jsHdService.removeById(id));
    }

    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<Long> ids){
        return  remove(jsHdService.removeByIds(ids));
    }

    /**
     * 批量发布活动
     * @return
     */
    @PutMapping("/issueBatch")
    public ResponseUtil issueBatch(@RequestBody List<Integer> ids) {
        if (!jsHdService.issueBatch(ids)) {
            throw new WebException(UPDATE_FAIL);
        }
        return ResponseUtil.success("批量发布活动成功!");
    }

}
