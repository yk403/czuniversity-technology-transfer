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

	PageInfo FindTJsXqByTJsLbTJsLy(Query query);
	TJsXq selectByName(String name);
	boolean saveXq(TJsXq tJsXq) throws Exception;


	boolean removeByIdXq(Integer id);
	boolean passUpdateById(Integer id);
	boolean assistancePassUpdateById(Integer id);
	PageInfo PageByTJsFb(Query query);

	boolean disPassById(Map<String, Object> params);
	boolean assistanceDisPassById(Map<String, Object> params);

	boolean issueBatch(List<Integer> ids);
	boolean assistanceIssueBatch(List<Integer> ids);
	boolean updateTJsXq(TJsXq tJsXq);

	boolean assistanceUpdateTJsXq(TJsXq tJsXq);

	TJsXq selectById(Integer id);
}
