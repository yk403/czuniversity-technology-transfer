package com.itts.technologytransactionservice.service;

import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyBm;
import com.itts.technologytransactionservice.model.LyHd;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyHdService extends IService<LyHd> {
    PageInfo findLyHdFront(Map<String, Object> params);

    Boolean saveHd(LyHd lyHd);

    PageInfo findLyHdFrontUser(Map<String, Object> params);
    Long getUserId();
}
