package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyZp;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyZpAdminService extends IService<LyZp> {
    PageInfo findLyZpBack(Map<String, Object> params);

    Boolean saveZp(LyZp lyZp);
    Long getUserId();

    Boolean audit(Map<String, Object> params, Integer fbshzt);
}
