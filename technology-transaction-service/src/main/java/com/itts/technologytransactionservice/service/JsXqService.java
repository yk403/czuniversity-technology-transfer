package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsXq;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
public interface JsXqService extends IService<TJsXq> {

	PageInfo FindTJsXqByTJsLbTJsLy(Query query);

	TJsXq selectByName(String name);

	boolean saveXq(TJsXq tJsXq) throws Exception;


	boolean removeByIdXq(Integer id);

	boolean assistancePassUpdateById(Integer id);

	PageInfo PageByTJsFb(Query query);

	boolean assistanceDisPassById(Map<String, Object> params);

	boolean issueBatch(List<Integer> ids);

	boolean assistanceIssueBatch(List<Integer> ids);

	boolean updateTJsXq(TJsXq tJsXq);

	boolean assistanceUpdateTJsXq(TJsXq tJsXq);

	TJsXq selectById(Integer id);
}
