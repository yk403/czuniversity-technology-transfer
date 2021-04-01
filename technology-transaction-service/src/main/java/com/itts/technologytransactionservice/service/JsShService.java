package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsSh;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:13:04
 */
public interface JsShService extends IService<TJsSh> {

	IPage page(Query query);

	/**
	 * 根据成果id查询详情
	 * @param id
	 * @return
	 */
	TJsSh selectByCgId(Integer id);

	List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds);

	boolean deleteById(Integer cgId, Integer xqId);

	TJsSh selectByXqId(Integer id);

	Boolean auditCg(Map<String, Object> params, Integer fbshzt);

	Boolean auditXq(Map<String, Object> params, Integer fbshzt);

	List<TJsSh> selectBycgIds(List<Integer> cgIds);

	List<TJsSh> selectByxqIds(List<Integer> xqIds);
}
