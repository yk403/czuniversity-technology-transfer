package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsSh;

import java.util.List;
import java.util.Map;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术审核接口
 */
public interface JsShAdminService extends IService<TJsSh> {

    IPage page(Query query);

    List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds);

    Boolean auditCg(Map<String, Object> params, Integer fbshzt);

    Boolean auditXq(Map<String, Object> params, Integer fbshzt);

    Boolean assistanceAuditXq(Map<String, Object> params, Integer assistanceStatus);

    Boolean assistanceAuditCg(Map<String, Object> params, Integer assistanceStatus);

    List<TJsSh> selectByCgIds(List<Integer> ids);
}
