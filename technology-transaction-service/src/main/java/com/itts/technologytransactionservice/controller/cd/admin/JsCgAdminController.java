package com.itts.technologytransactionservice.controller.cd.admin;


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
import static com.itts.common.enums.ErrorCodeEnum.NAME_EXIST_ERROR;
import static com.itts.common.enums.ErrorCodeEnum.SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR;


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
    private JsCgAdminService JsCgAdminService;


    @Autowired
    private JsShAdminService JsShAdminService;

    /**
     * 分页条件查询成果(后台审批管理(用户录入信息))
     * @param params
     * @return
     */
    @PostMapping("/page")
    public ResponseUtil findJsCg(@RequestBody Map<String, Object> params) {
        //查询用户录入成功信息列表
        PageInfo<TJsCg> page = JsCgAdminService.findJsCg(params);
        return ResponseUtil.success(page);
    }

    /**
    * 根据成果id查询详细信息
    * @param id
    * @return
    */
    @GetMapping("/getById/{id}")
    public ResponseUtil findById(@PathVariable("id") Integer id) {
        return ResponseUtil.success("查询成果详细信息成功!",JsCgAdminService.findById(id));
    }

    /**
     * 根据成果名称查询
     * @param cgmc
     * @return
     */
    @GetMapping("/getByName/{cgmc}")
    public ResponseUtil getByName(@PathVariable("cgmc") String cgmc) {
        return ResponseUtil.success(JsCgAdminService.selectByName(cgmc));
    }

    /**
     * 保存成果信息
     */
    @PostMapping("/save")
    public ResponseUtil save(@RequestBody TJsCg tJsCg) {
        if (tJsCg.getId() != null) {
            throw new WebException(SYSTEM_REQUEST_PARAMS_ILLEGAL_ERROR);
        }
        if (!JsCgAdminService.saveCg(tJsCg)) {
            return ResponseUtil.error(NAME_EXIST_ERROR);
        }
        return ResponseUtil.success("保存成果信息成功!");
    }

    /**
     * 修改成果信息
     */
    @RequestMapping("/update")
    public ResponseUtil update(@RequestBody TJsCg tJsCg) {
        JsCgAdminService.updateTJsCg(tJsCg);
        return ResponseUtil.success();
    }


    /**
     * 批量删除
     */
    @PostMapping("/removeBatch")
    public R removeBatch(@RequestBody List<String> ids){

        return  remove(JsCgAdminService.removeByIdsCg(ids));
    }

    /**
     * 成果下发
     * @param ids
     * @return
     */
    @PostMapping("/issueBatch")
    public R issueBatch(@RequestBody List<String> ids){
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return  remove(JsCgAdminService.issueBatch(list));
    }



    /**
     * 受理协办下发
     * @param ids
     * @return
     */
    @PostMapping("/assistanceIssueBatch")
    public R assistanceIssueBatch(@RequestBody List<String> ids){
        List<Integer> list = new ArrayList<>();
        for (String id : ids) {
            list.add(Integer.valueOf(id));
        }
        return  remove(JsCgAdminService.assistanceIssueBatch(list));
    }

}
