package com.itts.technologytransactionservice.feign.persontraining;

import com.itts.common.constant.SystemConstant;
import com.itts.common.utils.common.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* @Description: 调用智库专家服务
* @Param:
* @return:
* @Author: yukai
* @Date: 2021/6/3
*/
@FeignClient(value = "person-training-service")
public interface ZjService {

    /**
    * @Description: 查询专家列表
    * @Param: [pageNum, pageSize, yjly, name]
    * @return: com.itts.common.utils.common.ResponseUtil
    * @Author: yukai
    * @Date: 2021/6/3
    */
    @GetMapping(SystemConstant.ADMIN_BASE_URL + "/v1/zj/list/")
    ResponseUtil getList(@ApiParam(value = "当前页码") @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                         @ApiParam(value = "每页显示记录数") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                         @ApiParam(value = "研究领域(与技术领域数据字典相关)") @RequestParam(value = "yjly", required = false) String yjly,
                         @ApiParam(value = "名称") @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "lx", required = false) String lx);

}
