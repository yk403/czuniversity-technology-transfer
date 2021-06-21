package com.itts.technologytransactionservice.service;

import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyZw;
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
public interface LyZwService extends IService<LyZw> {
    PageInfo findLyZwFront(Map<String, Object> params);
    PageInfo findLyZwFrontSelect(Map<String, Object> params);
    Boolean saveZw(LyZw lyZw);
}
