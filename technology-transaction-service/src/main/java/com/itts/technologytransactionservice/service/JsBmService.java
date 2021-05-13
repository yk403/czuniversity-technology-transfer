package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsBm;
import com.itts.technologytransactionservice.model.TJsLb;

import java.util.List;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:14:18
 */
public interface JsBmService extends IService<TJsBm> {

	PageInfo page(Query query);
	/**
	 * 删除报名信息
	 * @param id
	 * @return
	 */
	boolean removeByIdBm(String id);
	/**
	 * 删除报名信息(批量)
	 * @param id
	 * @return
	 */
	boolean removeByIdsBm(List<String> ids);

    boolean saveBm(TJsBm tJsBm);

	Long getUserId();
}
