package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsLb;

/**
 * 技术类别接口
 */
public interface JsLbAdminService extends IService<TJsLb> {
    PageInfo page(Query query);
}
