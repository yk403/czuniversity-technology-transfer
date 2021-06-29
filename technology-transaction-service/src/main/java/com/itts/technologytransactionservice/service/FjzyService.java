package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.Fjzy;

/**
 * <p>
 * 附件资源表 服务类
 * </p>
 *
 * @author liuyingming
 * @since 2021-05-21
 */
public interface FjzyService extends IService<Fjzy> {
    PageInfo page(Query query);
}
