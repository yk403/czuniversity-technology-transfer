package com.itts.technologytransactionservice.service.cd;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.cd.TCd;

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

    PageInfo<TCd> getByPage(Integer pageNum, Integer pageSize);

    ResponseUtil testFeign();

}
