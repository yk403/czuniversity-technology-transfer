package com.itts.technologytransactionservice.service.cd;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.itts.technologytransactionservice.model.LyMsg;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yukai
 * @since 2021-05-18
 */
public interface LyMsgAdminService extends IService<LyMsg> {
    PageInfo findLyMsgBack(Map<String, Object> params);

    Boolean saveMsg(LyMsg lyMsg);
    Long getUserId();
}
