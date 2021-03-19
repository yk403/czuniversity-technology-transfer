package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsCg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 09:16:14
 */
public interface ITJsCgService extends IService<TJsCg> {

	IPage page(Query query);

TJsCg selectByName(String name);
IPage FindtJsCgByTJsLbTJsLy(Query query);

	boolean saveCg(TJsCg tJsCg) throws Exception;
	boolean removeByIdCg(Long id);
	boolean disPassById(Map<String, Object> params);
	boolean passUpdateById(Long id);
	boolean issueBatch(List<Long> ids);

    boolean updateTJsCg(TJsCg tJsCg);

    boolean assistanceUpdateTJsCg(TJsCg tJsCg);

	boolean assistancePassUpdateById(Long id);

	boolean assistanceDisPassById(Map<String, Object> params);

	boolean assistanceIssueBatch(List<Long> longs);
}
