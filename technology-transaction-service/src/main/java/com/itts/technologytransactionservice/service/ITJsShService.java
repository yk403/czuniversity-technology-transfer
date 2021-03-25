package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsSh;

import java.util.List;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:13:04
 */
public interface ITJsShService extends IService<TJsSh> {

	IPage page(Query query);

	TJsSh selectBycgxqId(Integer cgxqId);

	List<TJsSh> selectBycgxqIds(List<Integer> cgxqIds);

	/*int updateById(Long id);*/
}
