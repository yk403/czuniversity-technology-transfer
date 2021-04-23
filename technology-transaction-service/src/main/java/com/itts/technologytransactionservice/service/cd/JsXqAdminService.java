package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsXq;

import java.util.List;
import java.util.Map;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术需求接口
 */
public interface JsXqAdminService extends IService<TJsXq> {

    PageInfo<TJsXq> findJsXq(Map<String, Object> params);

    TJsXq getById(Integer id);

    TJsXq selectByName(String name);

    boolean saveXq(TJsXq tJsXq);

    void updateTJsXq(TJsXq tJsXq);

    boolean removeByXqId(Integer id);

    PageInfo PageByTJsFb(Query query);

    boolean issueBatch(List<Integer> ids);

    boolean assistanceIssueBatch(List<Integer> ids);


}
