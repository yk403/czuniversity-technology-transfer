package com.itts.technologytransactionservice.service.cd;

import com.github.pagehelper.PageInfo;
import com.itts.common.utils.ResponseUtil;
import com.itts.technologytransactionservice.model.TCd;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lym
 * @since 2021-03-12
 */
public interface TCdService {

    /**
     * 查询列表
     */
    PageInfo<TCd> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 获取详情
     */
    TCd get(Long id);

    /**
     * 新增
     */
    TCd add(TCd tCd);

    /**
     * 更新
     */
    TCd update(TCd tCd);

    ResponseUtil testFeign();

}
