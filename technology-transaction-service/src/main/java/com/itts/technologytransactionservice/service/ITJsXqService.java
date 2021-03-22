package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsXq;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:19:20
 */
public interface ITJsXqService extends IService<TJsXq> {

	/*IPage page(Query query);*/
	PageInfo FindTJsXqByTJsLbTJsLy(Query query);
	TJsXq selectByName(String name);
	boolean saveXq(TJsXq tJsXq) throws Exception;


	boolean removeByIdXq(Long id);
	boolean passUpdateById(Long id);
	boolean assistancePassUpdateById(Long id);
	IPage PageByTJsFb(Query query);

	boolean disPassById(Map<String, Object> params);
	boolean assistanceDisPassById(Map<String, Object> params);

	boolean issueBatch(List<Long> ids);
	boolean assistanceIssueBatch(List<Long> ids);
	boolean updateTJsXq(TJsXq tJsXq);

	boolean assistanceUpdateTJsXq(TJsXq tJsXq);
}
