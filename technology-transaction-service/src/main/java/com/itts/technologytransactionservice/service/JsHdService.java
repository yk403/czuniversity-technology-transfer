package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.JsHdDTO;
import com.itts.technologytransactionservice.model.TJsHd;

import java.util.List;


/**
 * @Author: Austin
 * @Data: 2021/3/26
 * @Description: 技术活动管理接口
 */
public interface JsHdService extends IService<TJsHd> {

	PageInfo page(Query query);
    PageInfo pageFront1(Query query);

    boolean add(JsHdDTO jsHdDTO);

    boolean issueBatch(List<Integer> ids);

    boolean removeByIdHd(Integer id);
    Long getUserId();
}
