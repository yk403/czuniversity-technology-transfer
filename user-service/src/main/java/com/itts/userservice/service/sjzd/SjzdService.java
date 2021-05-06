package com.itts.userservice.service.sjzd;

import com.github.pagehelper.PageInfo;
import com.itts.userservice.model.sjzd.Sjzd;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author fl
 * @since 2021-03-31
 */
public interface SjzdService {

    /**
     * 获取列表 - 分页
     */
    PageInfo<Sjzd> findByPage(Integer pageNum, Integer pageSize, String model, String systemType, String dictionary);

    /**
     * 获取通过id
     */
    Sjzd get(Long id);

    /**
     * 查询，通过名称或编码
     */
    PageInfo<Sjzd> selectByString(Integer pageNum, Integer pageSize, String string, String ssmk);

    /**
     * 新增
     */
    Sjzd add(Sjzd sjzd);

    /**
     * 更新
     */
    Sjzd update(Sjzd sjzd);
}
