package com.itts.technologytransactionservice.service;

import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TCd;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lym
 * @since 2021-03-12
 */
public interface TCdService {

    List<TCd> getList();

    ResponseUtil testFeign();

}
