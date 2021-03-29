package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsJj;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:15:49
 */
public interface JsJjService extends IService<TJsJj> {

	IPage page(Query query);

}
