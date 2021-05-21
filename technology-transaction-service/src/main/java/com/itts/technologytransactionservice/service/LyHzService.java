package com.itts.technologytransactionservice.service;

import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyHz;
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
public interface LyHzService extends IService<LyHz> {
    PageInfo findLyHzFront(Map<String, Object> params);

    Boolean saveHz(LyHz lyHz);
}
