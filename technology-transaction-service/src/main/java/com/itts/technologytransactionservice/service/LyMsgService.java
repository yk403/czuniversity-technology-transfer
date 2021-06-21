package com.itts.technologytransactionservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyMsg;
import com.itts.technologytransactionservice.model.LyRy;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyMsgService extends IService<LyMsg> {
    PageInfo findLyMsgFront(Map<String, Object> params);

    Boolean saveMsg(LyMsg lyMsg);
}
