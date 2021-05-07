package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsCg;
import com.itts.technologytransactionservice.model.TJsXq;
import io.swagger.models.auth.In;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
public interface JsXqService extends IService<TJsXq> {

    /**
     * 已发布的需求招标申请(受理协办)
     *
     * @param params
     * @return
     */
    boolean assistanceUpdateTJsXq(Map<String, Object> params, Integer jylx) throws ParseException;

    /**
     * 分页条件查询需求(前台)
     *
     * @param params
     * @return
     */
    PageInfo findJsXqFront(Map<String, Object> params);

    TJsXq selectByName(String name);

    boolean saveXq(TJsXq tJsXq) throws Exception;


    boolean removeByIdXq(Integer id);

    boolean assistancePassUpdateById(Integer id);

    PageInfo PageByTJsFb(Query query);

    boolean assistanceDisPassById(Map<String, Object> params);

    boolean issueBatch(List<Integer> ids);

    boolean assistanceIssueBatch(List<Integer> ids);

    boolean updateTJsXq(TJsXq tJsXq);

    /**
     * 分页条件查询需求(个人详情)
     *
     * @param params
     * @return
     */
    PageInfo<TJsXq> findJsXqUser(Map<String, Object> params);

    /**
     * 个人发布审核需求申请(0待提交;1待审核;2通过;3整改;4拒绝)
     *
     * @param params
     * @return
     */
    boolean auditXq(Map<String, Object> params, Integer fbshzt);
}
