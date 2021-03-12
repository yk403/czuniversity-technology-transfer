package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TZj;


/**
 * 
 * 
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-20 17:05:41
 */
public interface ITZjService extends IService<TZj> {

	IPage page(Query query);

}
