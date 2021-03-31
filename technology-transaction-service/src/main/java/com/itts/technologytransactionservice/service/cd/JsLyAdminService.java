package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.common.utils.Query;
import com.itts.technologytransactionservice.model.TJsLy;

/**
 * 技术领域接口
 */
public interface JsLyAdminService extends IService<TJsLy> {
    PageInfo page(Query query);
}
