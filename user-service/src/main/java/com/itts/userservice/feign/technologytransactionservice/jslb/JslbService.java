package com.itts.userservice.feign.technologytransactionservice.jslb;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.R;
import com.itts.common.utils.common.ResponseUtil;
import com.itts.userservice.model.jslb.TJsLb;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
 *@Auther: yukai
 *@Date: 2021/08/17/16:02
 *@Desription:
 */
@FeignClient(value = "technology-transaction-service")
public interface JslbService {
    /***
    * @Description: 类别新增
    * @Param: [file]
    * @return: com.itts.common.utils.common.ResponseUtil
    * @Author: yukai
    * @Date: 2021/8/17
    */
    @PostMapping(SystemConstant.ADMIN_BASE_URL + "/v1/JsLb/save")
    R save(@RequestBody TJsLb tJsLb);
    /***
    * @Description: 类别修改
    * @Param: [tJsLb]
    * @return: com.itts.common.utils.R
    * @Author: yukai
    * @Date: 2021/8/17
    */
    @RequestMapping(SystemConstant.ADMIN_BASE_URL + "/v1/JsLb/update")
    R update(@RequestBody TJsLb tJsLb);
    /***
     * @Description: 类别删除
     * @Param: [tJsLb]
     * @return: com.itts.common.utils.R
     * @Author: yukai
     * @Date: 2021/8/17
     */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/JsLb/remove/{id}")
    R remove(@PathVariable("id") String id);



}
