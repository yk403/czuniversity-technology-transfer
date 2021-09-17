package com.itts.technologytransactionservice.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.JsMsg;
import com.itts.technologytransactionservice.model.JsMsgDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fuli
 * @since 2021-09-17
 */
public interface JsMsgService extends IService<JsMsg> {

    PageInfo<JsMsgDTO> findPage(Integer pageNum,Integer pageSize);
}
