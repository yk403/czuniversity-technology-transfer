package com.itts.technologytransactionservice.service;

import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyBm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-17
 */
public interface LyBmService extends IService<LyBm> {

    PageInfo findLyBmFront(Map<String, Object> params);

    Boolean saveBm(LyBm lyBm);
}
