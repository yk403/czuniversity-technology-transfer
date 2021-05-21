package com.itts.technologytransactionservice.service;

import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyLy;
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
public interface LyLyService extends IService<LyLy> {
    PageInfo findLyLyFront(Map<String, Object> params);

    Boolean saveLy(LyLy lyLy);
}
