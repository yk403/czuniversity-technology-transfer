package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsLb;


/**
 * @author xp
 * @email 15161575502@163.com
 * @date 2021-02-22 11:14:18
 */
public interface JsLbService extends IService<TJsLb> {

    PageInfo page(Query query);

}
